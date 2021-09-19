# Employee vaccination inventory

## Prerequisites

- Java 8
- Spring Boot 2.5.4
- Swagger Open API 3
- Postgres 11.x running in a Docker container
- Maven 3.6.3

## Installation

### Build and run Postgres in a container

Build image and run container with Docker Compose:

`docker-compose up --build -d`

Stop and remove networks and containers for services defined in the Compose file.

`docker-compose down -v --remove-orphans`

Then, compile and run the application.

### Execute shell command inside container

Execute shell command inside container to initialize some sample records in the database

`docker exec -it dev-postgres bash`

`psql -U user_test -d db_test -a -f sample.sql`

## Test the application

To test the application use the following administrator username and password:

- username: april
- password: 1234

## Data model

![DataModel](https://i.postimg.cc/23Nz386M/Data-model.jpg)

## Swagger Paths

- Swagger UI endpoint: http://localhost:8080/swagger-ui/index.html
- All Swagger Resources(groups): http://localhost:8080/swagger-resources
- Metadatos endpoint: http://localhost:8080/v3/api-docs

## Getting Started - Spring Boot

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
