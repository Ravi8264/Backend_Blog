# Use OpenJDK 21 as base image (matches pom.xml)
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better layer caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true

# Create uploads directory
RUN mkdir -p uploads/images

# Expose port (Railway will set the PORT environment variable)
EXPOSE $PORT

# Run the application with production profile
CMD ["sh", "-c", "java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/blog-0.0.1-SNAPSHOT.jar"]
