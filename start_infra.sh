#!/bin/sh

echo "Spins up infrastructure for local debugging -- you have to run 'pantry' and 'orders' apps on your own"
echo "Spinning kafka broker, zookeeper, schema registry and postgresql locally..."
docker-compose -f infra-setup.yml -f kafka-setup.yml up -d --build
echo " - To see the docker containers running, simply run:
                      $ docker ps -a"
echo " - When you've finished you can tear down the infra, please run:
                      $ ./down.sh"