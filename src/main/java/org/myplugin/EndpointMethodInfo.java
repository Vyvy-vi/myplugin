package org.myplugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndpointMethodInfo {
    public String method;
    public String endpoint;


    public static final Pattern methodPattern = Pattern.compile("(?<=@)(.*?)(?=M)");
    public static final Pattern endpointPattern = Pattern.compile("(?<=\\(\")(.*?)(?=\"\\))");


    public EndpointMethodInfo(String method, String endpoint) {
        this.method = method;
        this.endpoint = endpoint;
    }

    public EndpointMethodInfo(String mapping) {
        Matcher mP = methodPattern.matcher(mapping);
        Matcher eP = endpointPattern.matcher(mapping);

        if (mP.find()) {
            this.method = mP.group(1).toUpperCase();
        } else {
            this.method = "";
        }

        if (eP.find()) {
            this.endpoint = eP.group(1);
        } else {
            this.endpoint = "";
        }
    }
}
