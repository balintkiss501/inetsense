#!/bin/bash

. setvars.sh

cd $INETSENSE_INSTALL_DIR/$INETSENSE_WEB_DIR_NAME

$JAVA_HOME/bin/java $WEB_JAVA_OPT -jar $INETSENSE_INSTALL_DIR/$INETSENSE_WEB_DIR_NAME/inetsense-server-web-${project.version}.jar
