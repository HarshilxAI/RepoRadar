@ECHO OFF
SETLOCAL

SET "MAVEN_VERSION=3.9.9"
SET "MAVEN_BASE_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries"
SET "MAVEN_ZIP=apache-maven-%MAVEN_VERSION%-bin.zip"
SET "WRAPPER_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%-bin"
SET "MAVEN_HOME=%WRAPPER_HOME%\apache-maven-%MAVEN_VERSION%"
SET "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

IF NOT EXIST "%MAVEN_CMD%" (
  ECHO Downloading Apache Maven %MAVEN_VERSION% for local wrapper use...
  IF NOT EXIST "%WRAPPER_HOME%" MKDIR "%WRAPPER_HOME%"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $zipPath=Join-Path $env:WRAPPER_HOME $env:MAVEN_ZIP; Invoke-WebRequest -UseBasicParsing -Uri ($env:MAVEN_BASE_URL + '/' + $env:MAVEN_ZIP) -OutFile $zipPath; Expand-Archive -Path $zipPath -DestinationPath $env:WRAPPER_HOME -Force; Remove-Item $zipPath -Force"
  IF ERRORLEVEL 1 (
    ECHO Failed to download Maven wrapper distribution.
    EXIT /B 1
  )
)

CALL "%MAVEN_CMD%" %*
EXIT /B %ERRORLEVEL%
