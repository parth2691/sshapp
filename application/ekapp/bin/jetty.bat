@echo off
echo [INFO] Use maven jetty-plugin run the project.

cd %~dp0
cd ..
set MAVEN_OPTS=%MAVEN_OPTS% -Xmx512m -XX:MaxPermSize=256m
call mvn -o jetty:run -Djetty.port=8089 -Dmaven.test.skip=true
cd bin
pause