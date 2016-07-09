
call setvars.bat

cd ..\..\dist\static\jnlp

%JAVA_HOME%\bin\jarsigner -tsa http://timestamp.digicert.com -keystore keystore.jks -storepass server inetsense-probe-desktop-${project.version}.jar server

cd ..\..

%JAVA_HOME%\bin\jar uf inetsense-server-collector-${project.version}.jar static\jnlp\inetsense-probe-desktop-${project.version}.jar

cd ..\scripts\win

xcopy /y ..\..\dist\config\collector-server.properties %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\
xcopy /y ..\..\dist\inetsense-server-collector-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\
