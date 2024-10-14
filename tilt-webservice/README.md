This application is built using Kotlin, Tomcat, and Spring Boot.

### Requirements:
- Java 17
- Tomcat (local includes embedded Tomcat)
- MySQL (local includes embedded Sqlite)

### Running Locally

To run locally, from the `tilt-webservice` project root:
```
gradlew.bat bootRun --args='--spring.profiles.active=development'
```

Once running, access API docs via
http://localhost:8080/brew/swagger-ui.html

