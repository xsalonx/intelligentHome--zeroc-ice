#!/usr/bin/env bash
SCRIPT_PATH=$(dirname "$0")

icegridregistry --Ice.Config=./register.cfg

#java -jar "$SCRIPT_PATH/server/build/lib/server.jar"
#java -jar "$SCRIPT_PATH/client/build/lib/client.jar"