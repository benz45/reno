#!/bin/sh
echo "Start"
echo ACTIVE_PROFILE: $ACTIVE_PROFILE
echo API_VERSION: $API_VERSION
echo PROJECT_NAME: $PROJECT_NAME

echo "Get Environment Variables"
aws configure set aws_access_key_id ${AWS_ACCESS_KEY_ID}
aws configure set aws_secret_access_key ${AWS_SECRET_ACCESS_KEY}
aws configure set default.region ap-southeast-1
aws configure set default.output json
aws secretsmanager get-secret-value --secret-id arn:aws:secretsmanager:ap-southeast-1:772962499539:secret:secret/spartan/db-0fkj3U --query SecretString --output text | jq -r 'to_entries|map("\(.key)=\(.value)")|.[]' > .env
export $(xargs <.env)
export SPRING_DATASOURCE_URL=jdbc:postgresql://${SECRET_ENV_DB_MASTER_URL}:${SECRET_ENV_DB_PORT}/${SECRET_ENV_DB_DATABASE_NAME}
export SPRING_DATASOURCE_USERNAME=${SECRET_ENV_DB_USERNAME}
export SPRING_DATASOURCE_PASSWORD=${SECRET_ENV_DB_PASSWORD}

cd /app
jar uf /app/target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar BOOT-INF/classes/*
java -jar $JAVA_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE /app/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar
