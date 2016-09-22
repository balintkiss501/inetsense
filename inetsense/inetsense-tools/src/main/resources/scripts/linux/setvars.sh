#!/bin/bash

JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_74.jdk/Contents/Home"

INETSENSE_INSTALL_DIR="/Users/zdtorok/inetsense-install"
INETSENSE_CLOCK_DIR_NAME="clock-server"
INETSENSE_COLLECTOR_DIR_NAME="collector-server"
INETSENSE_UPLOAD_DIR_NAME="upload-server"
INETSENSE_WEB_DIR_NAME="web-server"

DB_HOST="localhost"
DB_PORT=8889
DB_NAME="inetsense"
DB_USER="isuser"
DB_PWD="isuser"
DB_URL="jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME"

COLLECTOR_DEBUG_PORT=8000
CLOCK_DEBUG_PORT=8010
UPLOAD_DEBUG_PORT=8020
WEB_DEBUG_PORT=8030

COLLECTOR_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=$COLLECTOR_DEBUG_PORT$,suspend=n"
COLLECTOR_JAVA_OPT="$COLLECTOR_JAVA_OPT -Dspring.datasource.url=$DB_URL?autoReconnect=true&useSSL=false"
COLLECTOR_JAVA_OPT="$COLLECTOR_JAVA_OPT -Dspring.datasource.username=$DB_USER"
COLLECTOR_JAVA_OPT="$COLLECTOR_JAVA_OPT -Dspring.datasource.password=$DB_PWD"
COLLECTOR_JAVA_OPT="$COLLECTOR_JAVA_OPT -Dspring.config.location=collector-server.properties"

CLOCK_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=$CLOCK_DEBUG_PORT,suspend=n"
CLOCK_JAVA_OPT="$CLOCK_JAVA_OPT -Dspring.config.location=clock-server.properties"

UPLOAD_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=$UPLOAD_DEBUG_PORT,suspend=n"

WEB_JAVA_OPT="-agentlib:jdwp=transport=dt_socket,server=y,address=$WEB_DEBUG_PORT,suspend=n"
WEB_JAVA_OPT="$WEB_JAVA_OPT -Dspring.datasource.url=$DB_URL?autoReconnect=true&useSSL=false"
WEB_JAVA_OPT="$WEB_JAVA_OPT -Dspring.datasource.username=$DB_USER"
WEB_JAVA_OPT="$WEB_JAVA_OPT -Dspring.datasource.password=$DB_PWD"
WEB_JAVA_OPT="$WEB_JAVA_OPT -Dspring.config.location=web-server.properties"
