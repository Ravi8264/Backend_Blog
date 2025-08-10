#!/bin/bash

# Startup script for the Blog API
echo "Starting Blog API..."

# Set default values if environment variables are not set
export PORT=${PORT:-8080}
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}

# Print environment variables for debugging
echo "PORT: $PORT"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo "DATABASE_URL: ${DATABASE_URL:-not_set}"

# Start the application
exec java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
     -Dserver.port=$PORT \
     -Xmx512m \
     -XX:MaxMetaspaceSize=128m \
     -jar target/blog-0.0.1-SNAPSHOT.jar
