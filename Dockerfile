# Use Java runtime base image
FROM maven:3.8-openjdk-17-slim

# Add spring user and continue with that user
RUN groupadd -f spring
RUN useradd -m -g spring spring
USER spring:spring

# Set working directory in container
WORKDIR /app

# Copy jar-file and pom.xml into working directory
COPY pom.xml .
COPY target/*.jar app.jar

# Expose Spring Boot port (8080)
EXPOSE 8080

# Start Spring Boot App at container start
ENTRYPOINT ["java", "-jar", "app.jar"]