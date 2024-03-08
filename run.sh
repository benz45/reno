#!/bin/sh
echo "Start"
echo ACTIVE_PROFILE: $ACTIVE_PROFILE
echo API_VERSION: $API_VERSION
echo PROJECT_NAME: $PROJECT_NAME

./rogue.sh '/app/BOOT-INF/classes/application-template.properties' "$PROJECT_CONFIG" "$ACTIVE_PROFILE"

jar uf /app/target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar /app/BOOT-INF/classes/*
java -jar $JAVA_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE -Dapi.version=$API_VERSION -Dspring.config.location=/app/BOOT-INF/classes/application-$ACTIVE_PROFILE.properties /app/target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar
