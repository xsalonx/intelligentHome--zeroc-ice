#!/usr/bin/env bash
SCRIPT_PATH=$(dirname "$0")

sudo rm -rf registry_store tmp
sudo mkdir -p registry_store tmp
gradle clean
gradle build
docker-compose -f docker-compose.yml up --build -d
echo "trying to send xml conf"
sleep 2
icegridadmin -e "node list" --username "admin" --password "password"
icegridadmin -e "application add intelligentHomeDescription.xml" --username "admin" --password "password"
docker-compose up

# TODO
docker-compose rm --force
sudo rm -rf registry_store tmp
