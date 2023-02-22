# User registration API

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/nrudelle/offertechnicaltest.git
```

**2. Build and run the app using maven**

```bash
mvn clean package
java -jar userregistration-api/target/userregistration-api-1.0-SNAPSHOT.jar

```
You must build it with jdk 17 or higher and maven 3.8.1 or higher.

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    POST /users/register
    
    GET /users/{id}/getDetails

Api documentation will be available at <http://localhost:8080/swagger-ui/index.html#/>, 
or in json format at <http://localhost:8080/v3/api-docs> 
as soon as the application is started.