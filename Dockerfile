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

# Expose default port (will be overridden by deployment platform)
EXPOSE 8080

# Copy startup script
COPY start.sh ./
RUN chmod +x start.sh

# Run the application using startup script
CMD ["./start.sh"]
