@echo off

call setvars.bat

cd /D %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%

%JAVA_HOME%\bin\java.exe %COLLECTOR_JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\inetsense-server-collector-${project.version}.jar