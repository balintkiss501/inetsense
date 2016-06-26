@ECHO OFF


set JAVA_HOME="c:\jdk1.8.0_60"

rem enable remote debug
set JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%DEBUG_PORT%,suspend=n"

set INETSENSE_INSTALL_DIR="d:\install\inetsense"
set INETSENSE_COLLECTOR_DIR_NAME="collector-server"
set INETSENSE_UPLOAD_DIR_NAME="upload-server"