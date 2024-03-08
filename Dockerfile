FROM openjdk:18-jdk-slim
WORKDIR /app

ARG API_VERSION="latest"
ENV API_VERSION ${API_VERSION}
ENV PROJECT_NAME="reno"

ADD /target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar

RUN mkdir -p BOOT-INF/classes/
ADD src/main/resources/application-template.properties BOOT-INF/classes/

WORKDIR /app/src/main/resources

ADD ./rogue.sh ./rogue.sh
ADD ./run.sh ./run.sh

RUN sed -i 's/\r$//' rogue.sh
RUN sed -i 's/\r$//' run.sh

RUN chmod +x 'rogue.sh'
RUN chmod +x 'run.sh'