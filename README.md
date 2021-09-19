- Java 8
- Spring Boot 2.5.4
- Swagger Open API 3
- Postgres 11.x running in a Docker container
- Maven 3.6.3

## Postgres

### Build and run it

Build image and run container with Docker Compose:

`docker-compose up --build -d`

Stop and remove networks and containers for services defined in the Compose file.

`docker-compose down -v --remove-orphans`


### Execute shell command inside container

`docker exec -it dev-postgres bash`

`psql -U user_test -d db_test -a -f sample.sql`

## 

Default admin

- username: april
- password: 1234

## Swagger Paths

- Swagger UI endpoint: http://localhost:8080/swagger-ui/index.html
- All Swagger Resources(groups): http://localhost:8080/swagger-resources
- Metadatos endpoint: http://localhost:8080/v3/api-docs


## Data model

![DataModel](https://i.postimg.cc/23Nz386M/Data-model.jpg)


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

