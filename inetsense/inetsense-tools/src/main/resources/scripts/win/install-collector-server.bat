
call setvars.bat

xcopy /y ..\..\dist\config\collector-server.properties %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\
xcopy /y ..\..\dist\inetsense-server-collector-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_COLLECTOR_DIR_NAME%\
