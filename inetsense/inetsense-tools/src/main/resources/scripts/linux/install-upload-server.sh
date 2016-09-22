#!/bin/bash

. setvars.sh

mkdir -p $INETSENSE_INSTALL_DIR/$INETSENSE_UPLOAD_DIR_NAME
cp ../../dist/inetsense-server-upload-${project.version}.jar $INETSENSE_INSTALL_DIR/$INETSENSE_UPLOAD_DIR_NAME/
