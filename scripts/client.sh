#!/bin/bash
PROJECT_DIR="$(dirname $0)/../"

java -jar $PROJECT_DIR/client/build/libs/client.jar --Ice.Config="$PROJECT_DIR/config/client.cfg"