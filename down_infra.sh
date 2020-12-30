#!/bin/bash

echo "Tearing down infrastructure..."
docker-compose -f infra-setup.yml -f kafka-setup.yml down