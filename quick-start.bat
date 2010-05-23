@echo off
echo [INFO] ȷ��Ĭ��JDK�汾ΪJDK5.0�����ϰ汾.

echo [INFO] �粻������Maven�ٷ���վ, �޸ı��ļ�ȥ������һ�е�ע��.
set OFF_LINE=-o

set MVN=mvn
set ANT=ant

if exist "tools\maven\apache-maven-2.2.1\" set MVN="%cd%\tools\maven\apache-maven-2.2.1\bin\mvn.bat"
if exist "tools\ant\apache-ant-1.7.1\" set ANT="%cd%\tools\ant\apache-ant-1.7.1\bin\ant.bat"
echo Maven����Ϊ%MVN%
echo Ant����Ϊ%ANT%

echo [Step 1] ����tools/maven/central-repository �� %userprofile%\.m2\repository
rem xcopy /s/e/i/h/d/y "tools\maven\central-repository" "%USERPROFILE%\.m2\repository"

echo [Step 2] ����H2���ݿ�.
cd tools/h2
call %MVN% %OFF_LINE% initialize -Pstartdb
cd ..\..\

echo [Step 3] ��װSpringSide3 ����modules,examples��Ŀ������Maven�ֿ�,����Eclipse��Ŀ�ļ�.
call %MVN% %OFF_LINE% eclipse:clean eclipse:eclipse
call %MVN% %OFF_LINE% clean install -Dmaven.test.skip=true

echo [Step 4] ΪMini-Service ��ʼ�����ݿ�, ����Jetty.
cd examples\mini-service
call %ANT% -f bin/build.xml init-db 
start "Mini-Service" %MVN% %OFF_LINE% -Djetty.port=8084 jetty:run
cd ..\..\

echo [Step 5] ΪMini-Web ��ʼ�����ݿ�, ����Jetty.
cd examples\mini-web
call %ANT% -f bin/build.xml init-db 
start "Mini-Web" %MVN% %OFF_LINE% -Djetty.port=8085 jetty:run
cd ..\..\

echo [Step 6] ΪShowcase ����Eclipse��Ŀ�ļ�, ����, ���, ��ʼ�����ݿ�, ����Jetty.
cd examples\showcase
call %ANT% -f bin/build.xml init-db
start "Showcase" %MVN% %OFF_LINE% -Djetty.port=8086 jetty:run
cd ..\..\

echo [INFO] SpringSide3.0 �����������.
echo [INFO] �ɷ���������ʾ��ַ:
echo [INFO] http://localhost:8084/mini-service
echo [INFO] http://localhost:8085/mini-web
echo [INFO] http://localhost:8086/showcase

:end
pause