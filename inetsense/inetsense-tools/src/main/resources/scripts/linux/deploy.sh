#!/bin/bash

echo "Stopping previous servers..."
kill `ps -ef | grep 'server-upload' | grep -v grep | awk '{print $2}'`
curl -X POST http://localhost:8501/management/shutdown  # shutdown web
curl -X POST http://localhost:8502/management/shutdown  # shutdown collector
kill `ps -ef | grep 'server-web' | grep -v grep | awk '{print $2}'`
kill `ps -ef | grep 'server-collector' | grep -v grep | awk '{print $2}'`
echo "  Status: $?"

cd /home/bodoalex/inetsense-install-dir/
rm -rf *
cd /home/bodoalex/inetsense-bin

echo "Removing old dist..."
rm -rf dist
echo "  Status: $?"
echo "Removing old scripts..."
rm -rf scripts
echo "  Status: $?"

echo "Copying dist..."
cp -r "$WORKSPACE/inetsense/inetsense-tools/target/dist" .
echo "  Status: $?" 
echo "Copying scripts..."
cp -r "$WORKSPACE/inetsense/inetsense-tools/target/scripts" .
echo "  Status: $?"

echo "Copying setvars.sh..."
cp setvars.sh scripts/linux/
echo "  Status: $?"

echo "Entering scripts/linux and updating permissions..."
cd scripts/linux
echo "  Status: $?"
chmod 777 *.sh
echo "  Status: $?"

echo "Installing Collector Server..."
./install-collector-server.sh
echo "  Status: $?"

echo "Installing Upload Server..."
./install-upload-server.sh
echo "  Status: $?"

echo "Installing Web Server..."
./install-web-server.sh
mkdir /home/bodoalex/inetsense-install-dir/upload-server/log
echo "  Status: $?"

# It's important because otherwise Jenkins kills all child processes
export BUILD_ID=dontKillMe

echo "Starting Collector Server..."
nohup ./start-collector.sh </dev/null > collector-log 2>&1 &
echo "  Status: $?"

sleep 30
echo "Starting Upload Server..."
nohup ./start-upload.sh </dev/null > upload-log 2>&1 &
echo "  Status: $?"

echo "Starting Web Server..."
nohup ./start-web.sh </dev/null > web-log 2>&1 &
echo "  Status: $?"
