#!/bin/bash

. setvars.sh

mkdir -p $INETSENSE_INSTALL_DIR/$INETSENSE_CLOCK_DIR_NAME
cp ../../dist/config/clock-server.properties $INETSENSE_INSTALL_DIR/$INETSENSE_CLOCK_DIR_NAME/
cp ../../dist/inetsense-server-clock-${project.version}.jar $INETSENSE_INSTALL_DIR/$INETSENSE_CLOCK_DIR_NAME/
