# myplugin
This plugin was created after a session from Programming Pathshala on Maven, which involved a demonstration of creating Maven Plugins.

The plugin can identify which api endpoints are defined in which controller in a SpringBoot project, assuming that all the RequestMappings are found in a folder namesd controllers or controller or web or rest. If the folder is not found, it just goes to the path given in dummyLocation variable.

<img width="1412" alt="Screenshot 2024-05-05 at 1 08 28 AM" src="https://github.com/Vyvy-vi/myplugin/assets/62864373/230d5369-74a8-452a-92e7-f413e6f4de45">
<img width="1412" alt="Screenshot 2024-05-05 at 1 08 47 AM" src="https://github.com/Vyvy-vi/myplugin/assets/62864373/5e0b4497-de6c-40ae-abf1-350519a07bee">

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
3. In the main project, that is the SpringBoot project in which you want to run this plugin, open the pom.xml and add the following content:
```xml
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
Replace MYPLUGINJARPATH with the path to your compiled jar file. In my case, this was - `/Users/vy/repos/myplugin/target/myplugin-1.0-SNAPSHOT.jar`
4. Install the main project
```
mvn install
```
5. For main project, select the Plugins dropdown from IntelliJ's maven panel, click on install and double click install:install-file. This should add myplugin to the project's plugins.
<img width="327" alt="Screenshot 2024-05-05 at 1 06 24 AM" src="https://github.com/Vyvy-vi/myplugin/assets/62864373/6cb220ba-6dd8-43df-8b32-c1456f63e8c0">
6. Click on the new `my` dropdown in Plugins (it might have a different name?) and double click my:hello. This should run the plugin for the project, and the output should try to find a controllers/controller/rest/web folder and contain all the mappings from there.
<img width="404" alt="Screenshot 2024-05-05 at 1 07 03 AM" src="https://github.com/Vyvy-vi/myplugin/assets/62864373/237c12a7-1320-492b-8d12-0ab912aaa0ad">


