@echo off
echo [INFO] Use maven assembly-plugin package jsw+jetty+webapp zip.

cd %~dp0
cd ..
call mvn clean package -Dpackage.classifier=production -Dmaven.test.skip=true
cd bin
pause 