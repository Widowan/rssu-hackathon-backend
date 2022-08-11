#!/usr/bin/env bash
set -e
docker-compose up -d postgres
./mvnw clean spring-boot:build-image
docker-compose down
