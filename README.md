# myplugin
This plugin was created after a session from Programming Pathshala on Maven, which involved a demonstration of creating Maven Plugins.

The plugin can be used to identify which api endpoints are defined in which controller in a SpringBoot project, assuming that all the RequestMappings are found in a folder namesd controllers or controller or web or rest.

## Installation
1. Clone this project
```
git clone https://github.com/Vyvy-vi/myplugin
```
2. Open project in terminal and install it
```
cd myplugin
mvn install
```
3. In main project, that is the SpringBoot project in which you want to run this plugin, open the pom.xml and add the following content:
```
<plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-install-plugin</artifactId>
                <version>3.1.1</version>
                <goals>
                    <goal>install-file</goal>
                </goals>
                <configuration>
                    <groupId>org.myplugin</groupId>
                    <artifactId>myplugin</artifactId>
                    <version>1.0</version>
                    <packaging>jar</packaging>
                    <file>MYPLUGINPATH</file>
                    <generatePom>true</generatePom>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.myplugin</groupId>
                <artifactId>myplugin</artifactId>
                <version>1.0</version>
                <goals>
                    <goal>hello</goal>
                </goals>
            </plugin>
</plugins>
```
Replace MYPLUGINJARPATH with the path to your compiled jar file. In my case this was - `/Users/vy/repos/myplugin/target/myplugin-1.0-SNAPSHOT.jar`
4. Install main project
```
mvn install
```
5. For main project, select Plugins dropdown from IntelliJ's maven panel, and click on install and double click install:install-file. This should add myplugin to the project's plugins.
6. Click on the new `my` dropdown in Plugins (it might have different name?) and double click my:hello. This should run the plugin for the project, and the output should try to find a controllers/controller/rest/web folder, and contain all the mappings from there.
