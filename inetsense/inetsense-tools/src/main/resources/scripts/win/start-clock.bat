@echo off

call setvars.bat

cd /D %INETSENSE_INSTALL_DIR%\\%INETSENSE_CLOCK_DIR_NAME%

%JAVA_HOME%\bin\java.exe %CLOCK_JAVA_OPT% -jar %INETSENSE_INSTALL_DIR%\\%INETSENSE_CLOCK_DIR_NAME%\inetsense-server-clock-${project.version}.jar