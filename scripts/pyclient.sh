#!/bin/bash
PROJECT_DIR="$(dirname $0)/../"

python $PROJECT_DIR/pyclient/client.py --Ice.Config="$PROJECT_DIR/config/client.cfg"