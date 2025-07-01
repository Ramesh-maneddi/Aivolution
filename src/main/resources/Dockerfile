# Use official OpenJDK 17 runtime image
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the built Spring Boot JAR into the container
COPY target/myapp.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]