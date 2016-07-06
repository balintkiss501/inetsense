
call setvars.bat

xcopy /y ..\..\dist\inetsense-server-upload-${project.version}.jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_UPLOAD_DIR_NAME%\
