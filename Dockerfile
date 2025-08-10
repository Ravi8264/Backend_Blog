# syntax = docker/dockerfile:1.4
FROM maven:3.9.5-openjdk-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN --mount=type=cache,target=/root/.m2/repository ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build the application with cache mount
RUN --mount=type=cache,target=/root/.m2/repository ./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true

# Runtime stage
FROM openjdk:21-jre-slim

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create uploads directory
RUN mkdir -p /tmp/uploads

# Expose port
EXPOSE $PORT

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
CMD java -Dserver.port=$PORT -jar app.jar
