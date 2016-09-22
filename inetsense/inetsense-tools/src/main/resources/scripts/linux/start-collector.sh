#!/bin/bash

. setvars.sh

cd $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME

$JAVA_HOME/bin/java $COLLECTOR_JAVA_OPT -jar $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME/inetsense-server-collector-${project.version}.jar
