#!/bin/bash

. setvars.sh

cd $INETSENSE_INSTALL_DIR/$INETSENSE_UPLOAD_DIR_NAME

echo "================================="
echo UPLOAD_JAVA_OPT
echo "================================="

$JAVA_HOME/bin/java $UPLOAD_JAVA_OPT -jar $INETSENSE_INSTALL_DIR/$INETSENSE_UPLOAD_DIR_NAME/inetsense-server-upload-${project.version}.jar
