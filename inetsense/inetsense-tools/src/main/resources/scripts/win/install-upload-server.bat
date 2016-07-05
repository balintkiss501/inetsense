
call setvars.bat

xcopy /y ..\..\dist\config\upload-server.properties %INETSENSE_INSTALL_DIR%\\%INETSENSE_UPLOAD_DIR_NAME%\
xcopy /y ..\..\dist\inetsense-server-upload-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_UPLOAD_DIR_NAME%\
