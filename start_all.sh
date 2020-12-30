#!/bin/sh

echo "Generates Avro schemas java POJOs and builds multiproject"
./gradlew clean build shadowJar
echo "Spins up the whole stack..."
docker-compose -f infra-setup.yml -f kafka-setup.yml -f docker-compose.yml up -d --build
echo " - To see the docker containers running, simply run:
                      $ docker ps -a"
echo " - When you've finished you can tear down the infra, please run:
                      $ ./stop_all.sh"