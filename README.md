# Employee vaccination inventory

Backend for keeping track of employee vaccination status inventory.

## Prerequisites

- Java 8
- Spring Boot 2.5.4
- Postgres 11 running in a Docker container
- Maven 3.6.3


- Spring Boot OAuth2 Resource Server with JWT
- Role Based Access Control (RBAC) with Spring Boot and JWT
- Swagger UI (Swagger-OpenAPI 3) with Authentication

## Test

- JUnit 5
- MockMvc
- Mockito

## Installation

### Build and run Postgres in a container

Build image and run container with Docker Compose:

`docker-compose up --build -d`

Stop and remove networks and containers for services defined in the Compose file.

`docker-compose down -v --remove-orphans`

*Then, compile and run the application.*

### Execute shell command inside container

Execute shell command inside container to initialize some sample records in the database:

`docker exec -it dev-postgres bash`

`psql -U user_test -d db_test -a -f sample.sql`

### Run application

- Use `mvn clean install` in the project root directory to build the project.
- Run the main class, `com.suprerapp.firstdemo.SuperappApplication` to start the application.

## Test the application

To test the application use the following administrator username and password:

- username: tom@acme.com
- password: 1234

## Data model

![DataModel](https://i.postimg.cc/23Nz386M/Data-model.jpg)

## Swagger Paths

- Swagger UI endpoint: http://localhost:8080/swagger-ui/index.html
- All Swagger Resources(groups): http://localhost:8080/swagger-resources
- Metadata endpoint: http://localhost:8080/v3/api-docs



## More resources about Spring Boot

### Reference Documentation

For further reference, please consider the following sections:

* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
