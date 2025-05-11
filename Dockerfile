# ----------- Stage 1: Build the JAR -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy all project files
COPY . .

# Build the Spring Boot JAR (skip tests for faster CI/CD)
RUN mvn clean package -DskipTests


# ----------- Stage 2: Run the JAR -----------
FROM eclipse-temurin:21-jdk-slim

ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
