@echo off
echo [INFO] �뱣֤�Ѱ�װspringside archetypes.

if exist generated-project (rmdir /s/q generated-project)
mkdir generated-project
cd generated-project

call mvn archetype:generate -DarchetypeCatalog=local

echo [INFO] ����%cd%\generated-project��������Ŀ.

echo [INFO] Ϊ����Ŀ��ʼ������jar.
cd generated-project
for /D %%a in (*) do cd "%%a"
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Dsilent=true
pause