
set DEBUG_PORT=8001

call setvars.bat

%JAVA_HOME%\bin\java.exe %JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\inetsense-server-upload-${project.version}.jar