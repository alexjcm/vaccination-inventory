# Build stage
FROM eclipse-temurin:24-jdk-alpine AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
# Pre-download gradle to cache it
RUN ./gradlew --version
COPY src ./src
RUN ./gradlew bootJar -x test

# Run stage
FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
