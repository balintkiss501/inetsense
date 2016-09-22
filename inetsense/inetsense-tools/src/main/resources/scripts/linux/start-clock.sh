#!/bin/bash

. setvars.sh

cd $INETSENSE_INSTALL_DIR/$INETSENSE_CLOCK_DIR_NAME

$JAVA_HOME/bin/java $CLOCK_JAVA_OPT -jar $INETSENSE_INSTALL_DIR/$INETSENSE_CLOCK_DIR_NAME/inetsense-server-clock-${project.version}.jar
