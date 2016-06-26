@ECHO OFF


set JAVA_HOME="c:\jdk1.8.0_60"

set INETSENSE_INSTALL_DIR="d:\install\inetsense"
set INETSENSE_COLLECTOR_DIR_NAME="collector-server"
set INETSENSE_UPLOAD_DIR_NAME="upload-server"

set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=inetsense
set DB_USER=inetsense
set DB_PWD=inetsense


rem enable remote debug
set JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%DEBUG_PORT%,suspend=n"

set JAVA_OPT=%JAVA_OPT% "-Dspring.datasource.url=jdbc:mysql://%DB_HOST%:%DB_PORT%/%DB_NAME%?autoReconnect=true&useSSL=false"
set JAVA_OPT=%JAVA_OPT% "-Dspring.datasource.username=%DB_USER%"
set JAVA_OPT=%JAVA_OPT% "-Dspring.datasource.password=%DB_PWD%"
