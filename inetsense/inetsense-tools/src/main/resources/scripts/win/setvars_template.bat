@echo off


set JAVA_HOME="c:\java\jdk1.8.0_92"

set INETSENSE_INSTALL_DIR="d:\install\inetsense"
set INETSENSE_CLOCK_DIR_NAME="clock-server"
set INETSENSE_COLLECTOR_DIR_NAME="collector-server"
set INETSENSE_UPLOAD_DIR_NAME="upload-server"
set INETSENSE_WEB_DIR_NAME="web-server"

set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=inetsense
set DB_USER=inetsense
set DB_PWD=inetsense
set DB_URL=jdbc:mysql://%DB_HOST%:%DB_PORT%/%DB_NAME%

set COLLECTOR_DEBUG_PORT=8000
set CLOCK_DEBUG_PORT=8010
set UPLOAD_DEBUG_PORT=8020
set WEB_DEBUG_PORT=8030

set COLLECTOR_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%COLLECTOR_DEBUG_PORT%,suspend=n"
set COLLECTOR_JAVA_OPT=%COLLECTOR_JAVA_OPT% "-Dspring.datasource.url=%DB_URL%?autoReconnect=true&useSSL=false"
set COLLECTOR_JAVA_OPT=%COLLECTOR_JAVA_OPT% "-Dspring.datasource.username=%DB_USER%"
set COLLECTOR_JAVA_OPT=%COLLECTOR_JAVA_OPT% "-Dspring.datasource.password=%DB_PWD%"
set COLLECTOR_JAVA_OPT=%COLLECTOR_JAVA_OPT% "-Dspring.config.location=collector-server.properties"


set CLOCK_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%CLOCK_DEBUG_PORT%,suspend=n"
set CLOCK_JAVA_OPT=%CLOCK_JAVA_OPT% "-Dspring.config.location=clock-server.properties"

set UPLOAD_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%UPLOAD_DEBUG_PORT%,suspend=n"

set WEB_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=%WEB_DEBUG_PORT%,suspend=n"
set WEB_JAVA_OPT=%WEB_JAVA_OPT% "-Dspring.datasource.url=%DB_URL%?autoReconnect=true&useSSL=false"
set WEB_JAVA_OPT=%WEB_JAVA_OPT% "-Dspring.datasource.username=%DB_USER%"
set WEB_JAVA_OPT=%WEB_JAVA_OPT% "-Dspring.datasource.password=%DB_PWD%"
set WEB_JAVA_OPT=%WEB_JAVA_OPT% "-Dspring.config.location=web-server.properties"

