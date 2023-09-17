#!/bin/sh

echo "Starting..."

echo ACTIVE_PROFILE: $ACTIVE_PROFILE
echo API_VERSION: $API_VERSION
echo JAVA_OPTS: $JAVA_OPTS
echo PROJECT_NAME: $PROJECT_NAME

java -jar $JAVA_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE /app/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar