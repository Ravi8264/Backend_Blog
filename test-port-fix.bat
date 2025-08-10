@echo off
echo === Testing PORT Environment Variable Fix ===

REM Test with problematic PORT value
echo.
echo Testing with PORT=$PORT (problematic case)
set PORT=$PORT
set SPRING_PROFILES_ACTIVE=dev

echo PORT environment variable is set to: %PORT%
echo SPRING_PROFILES_ACTIVE is set to: %SPRING_PROFILES_ACTIVE%

REM Build the application if needed
if not exist "target\blog-0.0.1-SNAPSHOT.jar" (
    echo Building application...
    call mvnw.cmd clean package -DskipTests
)

echo.
echo Starting application with PORT=$PORT...
echo This should now work without the NumberFormatException error.
echo.

REM Start the application
java -Dspring.profiles.active=%SPRING_PROFILES_ACTIVE% ^
     -Xmx512m ^
     -XX:MaxMetaspaceSize=128m ^
     -XX:+UseG1GC ^
     -jar "target\blog-0.0.1-SNAPSHOT.jar"
