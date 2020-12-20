#!/bin/sh

DIR=$PWD

echo "Spinning kafka broker and zookeeper locally..."

docker-compose -f "$DIR"/kafka.yml up -d

echo "To see the docker containers running, simply run:
                      $ docker ps -a"