# CocoaOwl_CRM


## Get the Code
```
git clone https://LORDs_diakonos@bitbucket.org/LORDs_diakonos/cocoaowl_crm.git
```

###Requirements

Java(v1.8)

Gradle (v3.0)


### To Run CocoaOwl_CRM project

On Windows
```
gradle wrapper --for maintain the unque gradle version
gradlew build -- for build the project
gradlew cargoRunLocal -- for run the project in tomcat server
```
on Unix-like platforms such as Linux and Mac OS X
```
gradle wrapper --for maintain the unque gradle version
./gradlew build -- for build the project
./gradlew cargoRunLocal -- for run the project in tomcat server
```
Note: When server starts will see following message 
```
":tomcatRunLocal
Press Ctrl-C to stop the container..."
```