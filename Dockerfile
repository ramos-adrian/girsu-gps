# Use the official Eclipse Temurin JDK image as the base image
FROM eclipse-temurin:21-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to the container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download the project dependencies
RUN ./mvnw dependency:go-offline

# Copy the project source code to the container
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw package -DskipTests

# Use the official Eclipse Temurin JDK image as the base image for the final stage
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage to the final stage
COPY --from=build /app/target/girsugps-0.0.1-SNAPSHOT.jar /app/backend.jar

# Expose the port the application will run on
EXPOSE 8080

# Set the entry point to run the Spring Boot application
CMD ["java", "-jar", "/app/backend.jar"]