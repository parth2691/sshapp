@echo off
echo [INFO] �뱣֤�Ѱ�װspringside archetypes.

if exist generated-project (rmdir /s/q generated-project)
mkdir generated-project
cd generated-project

call mvn -o archetype:generate -DarchetypeCatalog=local -DarchetypeArtifactId=maven-archetype-webapp

rem echo [INFO] ����%cd%\generated-project��������Ŀ.

rem echo [INFO] Ϊ����Ŀ��ʼ������jar.
rem cd generated-project
rem for /D %%a in (*) do cd "%%a"
rem call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true
rem call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Dsilent=true
pause