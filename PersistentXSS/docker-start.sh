#!/usr/bin/env bash

exec java \
    -Djava.security.egd=file:/dev/./urandom \
    -XX:MaxRAMPercentage=75.00 \
    -XX:MinRAMPercentage=25.00 \
    -XX:+UseParallelGC \
    -XshowSettings:vm \
    -jar service.jar --server.port=8080