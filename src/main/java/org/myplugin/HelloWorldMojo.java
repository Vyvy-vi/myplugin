package org.myplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @goal hello
 */
public class HelloWorldMojo extends AbstractMojo {
    private static final Map<String, List<EndpointMethodInfo>> annotations = new HashMap<>();

    public static Map<String, List<EndpointMethodInfo>> extractMappings(String directory) throws IOException {
        for (String filename : listFiles(directory)) {
            if (filename.endsWith(".java")) {
                String filepath = directory + "/" + filename;
                String topLevelEndpoint = "";
                boolean beforeClassDeclaration = true;
                try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("public class")) {
                            beforeClassDeclaration = false;
                        }
                        if (line.contains("Mapping")) {
                            if (beforeClassDeclaration) {
                                Matcher matcher = EndpointMethodInfo.endpointPattern.matcher(line.trim());
                                if (matcher.find()) topLevelEndpoint = matcher.group(1);

                            }
                            EndpointMethodInfo data = new EndpointMethodInfo(line.trim());
                            List<EndpointMethodInfo> mappings = annotations.getOrDefault(filename, new ArrayList<>());
                            if (!topLevelEndpoint.isEmpty()) {
                                data.endpoint = topLevelEndpoint + data.endpoint;
                            }
                            mappings.add(data);
                            annotations.put(filename, mappings);
                        }
                    }
                }
            }
        }
        return annotations;
    }

    private static String[] listFiles(String directory) {
        File dir = new File(directory);
        return dir.isDirectory() ? dir.list() : new String[0];
    }

    public static String dummyLocation = "/Users/vy/repos/dev-dialogue-api/src/main/java/com/devdialogue/backend/controllers/";

    private static String getControllersFolderPath(File dir) throws IOException {
        if (!dir.isDirectory()) {
            return null;
        }

        System.out.println("Checking in: " + dir.getCanonicalPath());

        if (dir.getName().equals("controllers") || dir.getName().equals("controller") || dir.getName().equals("web") || dir.getName().equals("rest")) {
            System.out.println("Found in: " + dir.getCanonicalPath());
            return dir.getCanonicalPath();
        }

        File[] subdirectories = dir.listFiles(File::isDirectory);

        if (subdirectories == null || subdirectories.length == 0) {
            return null;
        }

        for (File subdir : subdirectories) {
            String controllersPath = getControllersFolderPath(subdir);
            if (controllersPath != null) {
                return controllersPath;
            }
        }
        System.out.println("Checking dummy location");
        return dummyLocation;
    }


    @Override
    public void execute() throws MojoExecutionException {
        System.out.println("Hello World from Mojo Gojo :P");

        Map<String, List<EndpointMethodInfo>> annotations = null;

        try {
            annotations = extractMappings(getControllersFolderPath(new File(new java.io.File(".").getCanonicalPath() + "/src/main/java")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry<String, List<EndpointMethodInfo>> entry : annotations.entrySet()) {
            System.out.println("\nFile: " + entry.getKey());
            for (EndpointMethodInfo methodInfo : entry.getValue()) {
                System.out.println("\tMethod: " + methodInfo.method);
                System.out.println("\tEndpoint: " + methodInfo.endpoint);
            }
        }
    }
}
