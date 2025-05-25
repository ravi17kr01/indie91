# ----------- Stage 1: Build the JAR -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom.xml and settings for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src
RUN mvn clean package -DskipTests

# ----------- Stage 2: Run the JAR -----------
FROM eclipse-temurin:21-jdk-alpine

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
