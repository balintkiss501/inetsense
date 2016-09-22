#!/bin/bash

. setvars.sh

mkdir -p $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME
cp ../../dist/config/collector-server.properties $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME/
cp ../../dist/inetsense-server-collector-${project.version}.jar $INETSENSE_INSTALL_DIR/$INETSENSE_COLLECTOR_DIR_NAME/
