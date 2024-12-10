FROM openjdk:17-jdk-slim AS build
# Optional: If you need to build the project within the Dockerfile
# COPY . /app
# WORKDIR /app
# RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim
# Use an argument to specify the JAR file location dynamically
ARG JAR_FILE=target/*.jar

# Copy the JAR file dynamically based on the wildcard pattern
COPY ${JAR_FILE} app.jar

# Set entry point to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
