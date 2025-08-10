FROM maven:3.9.5-openjdk-21 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and make executable
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Download dependencies first (better layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src/ src/
RUN ./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true

# Runtime stage
FROM openjdk:21-jre-slim

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create uploads directory
RUN mkdir -p /tmp/uploads

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port
EXPOSE 8080

# Run the application
CMD java -Dserver.port=${PORT:-8080} -jar app.jar
