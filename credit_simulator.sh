#!/bin/bash

if [ ! -d "target" ]; then
  mvn clean package assembly:single
fi

java -jar target/credit-simulator-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"