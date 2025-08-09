#!/bin/bash
set -e

# Make mvnw executable
chmod +x ./mvnw

# Build the application
./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true

echo "Build completed successfully!"
