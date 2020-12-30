#!/bin/bash

echo "Stopping all..."
docker-compose -f infra-setup.yml -f kafka-setup.yml -f docker-compose.yml down