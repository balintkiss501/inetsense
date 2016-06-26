@echo off

set DEBUG_PORT=8000

call setvars.bat

cd /D %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%

%JAVA_HOME%\bin\java.exe %JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\inetsense-server-collector-${project.version}.jar