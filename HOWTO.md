# How to run application

### 1. IDE (IntelliJ IDEA is preferable):
* Build project with gradle build task (or from terminal/cmd)
* Run ParserApplication
* Embedded Tomcat is started by Spring MVC


### 2. Deploying .war file to Docker container
* Build project with gradle build task (or from terminal/cmd)
* run via terminal *sh run.sh* on Unix
* or run.bat via cmd.bat on Windows
* Tomcat on docker container is started


### 3. Test application
* Navigate to [http://localhost:8080/parser]
* From page above select any category
* For certain item - add path parameter next to category name (E.g. /cars/1)
* Not only happy path works:)