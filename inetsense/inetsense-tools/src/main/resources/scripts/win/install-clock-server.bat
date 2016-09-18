
call setvars.bat

xcopy /y ..\..\dist\config\clock-server.properties %INETSENSE_INSTALL_DIR%\\%INETSENSE_CLOCK_DIR_NAME%\
xcopy /y ..\..\dist\inetsense-server-clock-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_CLOCK_DIR_NAME%\
