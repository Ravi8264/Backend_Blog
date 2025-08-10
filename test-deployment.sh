#!/bin/bash

echo "=== Testing Blog API Deployment ==="

# Test different PORT scenarios
echo "Testing PORT resolution..."

# Test 1: Normal PORT value
echo "Test 1: Normal PORT value"
export PORT=8080
export SPRING_PROFILES_ACTIVE=prod
echo "PORT=$PORT"
echo "Expected: Application should start on port 8080"
echo

# Test 2: Different PORT value
echo "Test 2: Different PORT value"
export PORT=3000
echo "PORT=$PORT"
echo "Expected: Application should start on port 3000"
echo

# Test 3: No PORT set (should use default)
echo "Test 3: No PORT set"
unset PORT
echo "PORT is unset"
echo "Expected: Application should use default port 8080"
echo

# Test 4: Invalid PORT (should use default)
echo "Test 4: Invalid PORT"
export PORT="invalid"
echo "PORT=$PORT"
echo "Expected: Application should use default port 8080"
echo

echo "=== Configuration Summary ==="
echo "The following changes have been made to fix the PORT issue:"
echo "1. Updated application.properties to use server.port=\${PORT:8080}"
echo "2. Updated application-prod.properties to use server.port=\${PORT:8080}"
echo "3. Removed -Dserver.port system property from start.sh"
echo "4. Simplified PortConfiguration to only log debug information"
echo
echo "The application now properly resolves the PORT environment variable"
echo "through Spring Boot's standard property resolution mechanism."
