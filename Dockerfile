# Extend official Playwright for Java image
FROM mcr.microsoft.com/playwright/java:v1.30.0-focal

# Set the work directory for the application
WORKDIR /tests

# Copy the needed files to the app folder in Docker image
COPY gradle /tests/gradle
COPY src /tests/src
COPY build.gradle /tests
COPY gradle.properties /tests
COPY gradlew /tests
COPY settings.gradle /tests

# Run build witout tests to install dependencies
RUN ./gradlew build -x test
