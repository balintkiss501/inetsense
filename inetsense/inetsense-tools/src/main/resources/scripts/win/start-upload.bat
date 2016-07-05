@echo off

call setvars.bat

cd /D %INETSENSE_INSTALL_DIR%\\%INETSENSE_UPLOAD_DIR_NAME%

%JAVA_HOME%\bin\java.exe %UPLOAD_JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_UPLOAD_DIR_NAME%\inetsense-server-upload-${project.version}.jar