#!/bin/sh

echo "Spinning kafka broker and zookeeper locally..."
docker-compose -f infra-setup.yml up -d --build
echo "To see the docker containers running, simply run:
                      $ docker ps -a"