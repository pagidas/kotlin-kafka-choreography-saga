#!/bin/bash

PARTITIONS="3"
REPL_FACTOR="1"
ZOOKEEPER="zookeeper:2181"

echo "Waiting for Kafka to be ready..."
cub kafka-ready -b broker:29092 1 200
echo "Start creating topics"

echo "Creating if-not-exists topic: order-events"
kafka-topics --create --if-not-exists --zookeeper $ZOOKEEPER --partitions $PARTITIONS --replication-factor $REPL_FACTOR --topic order-events

echo "Creating if-not-exists topic: pantry-events"
kafka-topics --create --if-not-exists --zookeeper $ZOOKEEPER --partitions $PARTITIONS --replication-factor $REPL_FACTOR --topic pantry-events
