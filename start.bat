@echo off
echo === Starting Blog API ===

REM Set default values if environment variables are not set
if "%PORT%"=="" (
    set PORT=8080
    echo PORT not set, using default: 8080
) else if "%PORT%"=="$PORT" (
    echo ERROR: PORT contains literal $PORT string, fixing to default
    set PORT=8080
) else (
    echo Using PORT from environment: %PORT%
)

if "%SPRING_PROFILES_ACTIVE%"=="" (
    set SPRING_PROFILES_ACTIVE=prod
    echo SPRING_PROFILES_ACTIVE not set, using default: prod
) else (
    echo Using SPRING_PROFILES_ACTIVE from environment: %SPRING_PROFILES_ACTIVE%
)

REM Print environment variables for debugging
echo === Environment Variables ===
echo PORT: %PORT%
echo SPRING_PROFILES_ACTIVE: %SPRING_PROFILES_ACTIVE%
echo DATABASE_URL: %DATABASE_URL%
echo ============================

REM Check if JAR file exists
set JAR_FILE=target\blog-0.0.1-SNAPSHOT.jar
if not exist "%JAR_FILE%" (
    echo ERROR: JAR file not found: %JAR_FILE%
    echo Available files in target\:
    dir target\
    exit /b 1
)

echo Starting application with PORT=%PORT% and PROFILE=%SPRING_PROFILES_ACTIVE%

REM Start the application with explicit port override
set JAVA_OPTS=-Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%

REM Add port configuration only if PORT is valid number
echo Validating PORT: %PORT%
echo %PORT%| findstr /R "^[0-9]*$" >nul
if %errorlevel%==0 (
    echo Using validated PORT: %PORT%
    set JAVA_OPTS=%JAVA_OPTS% -Dserver.port=%PORT%
) else (
    echo Invalid PORT detected, using application.properties default
)

echo Final JAVA_OPTS: %JAVA_OPTS%

REM Start the application
java %JAVA_OPTS% ^
     -Xmx512m ^
     -XX:MaxMetaspaceSize=128m ^
     -XX:+UseG1GC ^
     -jar "%JAR_FILE%"
