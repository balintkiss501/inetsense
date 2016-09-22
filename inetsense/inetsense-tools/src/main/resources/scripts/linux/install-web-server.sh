#!/bin/bash

. setvars.sh

mkdir -p $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME

cp ../../dist/config/web-server.properties $INETSENSE_INSTALL_DIR/$INETSENSE_WEB_DIR_NAME/
cp ../../dist/inetsense-server-web-${project.version}.jar $INETSENSE_INSTALL_DIR/$INETSENSE_WEB_DIR_NAME/
