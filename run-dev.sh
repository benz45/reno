#!/bin/sh
echo "Start"
echo ACTIVE_PROFILE: $ACTIVE_PROFILE
echo API_VERSION: $API_VERSION
echo PROJECT_NAME: $PROJECT_NAME

java -jar $JAVA_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE -Dapi.version=$API_VERSION /app/target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar
