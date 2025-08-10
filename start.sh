#!/bin/bash

# Startup script for the Blog API
echo "=== Starting Blog API ==="

# Set default values if environment variables are not set
if [ -z "$PORT" ]; then
    export PORT=8080
    echo "PORT not set, using default: 8080"
else
    echo "Using PORT from environment: $PORT"
fi

if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    export SPRING_PROFILES_ACTIVE=prod
    echo "SPRING_PROFILES_ACTIVE not set, using default: prod"
else
    echo "Using SPRING_PROFILES_ACTIVE from environment: $SPRING_PROFILES_ACTIVE"
fi

# Validate PORT is a number and handle edge cases
if [[ "$PORT" == "\$PORT" ]] || [[ "$PORT" == '$PORT' ]]; then
    echo "ERROR: PORT contains literal \$PORT string, fixing to default"
    export PORT=8080
elif ! [[ "$PORT" =~ ^[0-9]+$ ]]; then
    echo "ERROR: PORT must be a number, got: $PORT"
    echo "Setting PORT to default: 8080"
    export PORT=8080
fi

# Print environment variables for debugging
echo "=== Environment Variables ==="
echo "PORT: $PORT"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo "DATABASE_URL: ${DATABASE_URL:-not_set}"
echo "JAVA_VERSION: $(java -version 2>&1 | head -1)"
echo "=========================="

# Check if JAR file exists
JAR_FILE="target/blog-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "ERROR: JAR file not found: $JAR_FILE"
    echo "Available files in target/:"
    ls -la target/ || echo "target/ directory not found"
    exit 1
fi

echo "Starting application with PORT=$PORT and PROFILE=$SPRING_PROFILES_ACTIVE"

# Clear any existing system properties that might interfere
unset SERVER_PORT 2>/dev/null || true

# Clear any Java system properties that might contain $PORT
unset _JAVA_OPTIONS 2>/dev/null || true
unset JAVA_TOOL_OPTIONS 2>/dev/null || true

# Remove any existing server.port system property from environment
if [[ "${!server.port@}" ]]; then
    unset server.port 2>/dev/null || true
fi

# Start the application - let Spring Boot handle port configuration via application.properties
# DO NOT set -Dserver.port as it can override environment variable resolution
export JAVA_OPTS="-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"

echo "PORT environment variable will be resolved by Spring Boot: $PORT"

echo "Final JAVA_OPTS: $JAVA_OPTS"

# Start the application
exec java $JAVA_OPTS \
     -Xmx512m \
     -XX:MaxMetaspaceSize=128m \
     -XX:+UseG1GC \
     -Djava.security.egd=file:/dev/./urandom \
     -jar "$JAR_FILE"
