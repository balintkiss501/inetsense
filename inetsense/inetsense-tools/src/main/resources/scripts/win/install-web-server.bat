
call setvars.bat

cd ..\scripts\win

xcopy /y ..\..\dist\config\web-server.properties %INETSENSE_INSTALL_DIR%\\%INETSENSE_WEB_DIR_NAME%\
xcopy /y ..\..\dist\inetsense-server-web-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_WEB_DIR_NAME%\
