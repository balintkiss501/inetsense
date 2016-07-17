@echo off

call setvars.bat

cd /D %INETSENSE_INSTALL_DIR%\\%INETSENSE_WEB_DIR_NAME%

%JAVA_HOME%\bin\java.exe %WEB_JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_WEB_DIR_NAME%\inetsense-server-web-${project.version}.jar