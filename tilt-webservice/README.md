This application is built using Kotlin, Tomcat, and Spring Boot.

### Requirements:
- Java 17
- Tomcat 9 (local includes embedded Tomcat)
- MySQL (MariaDB) (local includes embedded Sqlite)

### Running Locally

To run locally, from the `tilt-webservice` project root:
```bash
gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

Once running, access API docs via
http://localhost:8080/brew/swagger-ui.html
