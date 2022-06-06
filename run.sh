#!/usr/bin/env bash
SCRIPT_PATH=$(dirname "$0")

rm -rf registry_store
mkdir -p registry_store
gradle clean
gradle build
docker-compose -f docker-compose.yml up --build


#icegridregistry --Ice.Config=./register.cfg
#java -jar "$SCRIPT_PATH/server/build/lib/server.jar"
#java -jar "$SCRIPT_PATH/client/build/lib/client.jar"