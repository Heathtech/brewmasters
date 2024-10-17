This application is built using Kotlin, Tomcat, and Spring Boot.

### Requirements:
- Java 17
- Tomcat (local includes embedded Tomcat)
- MySQL (local includes embedded Sqlite)

### Running Locally

To run locally, from the `tilt-webservice` project root:
```
gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

Once running, access API docs via
http://localhost:8080/brew/swagger-ui.html

### Usage

PUT /beer/{name} to start a new brew schedule
- The body specifies the brew start date (if omitted, current server time is used)
- The 

