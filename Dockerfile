FROM openjdk:11-jre-slim
WORKDIR /app

ARG API_VERSION="latest"
ENV API_VERSION ${API_VERSION}
ENV PROJECT_NAME="reno"

# install python
RUN apt-get update
RUN apt-get -y install python3-pip

# install aws
RUN pip3 install awscli

# install jq
RUN apt-get update && apt-get install -y jq

RUN mkdir -p BOOT-INF/classes/
ADD src/main/resources/application-template.properties BOOT-INF/classes/

WORKDIR /app/src/main/resources

ADD ./run.sh ./run.sh
RUN sed -i 's/\r$//' run.sh
RUN chmod +x 'run.sh'

ADD /target/${PROJECT_NAME}-0.0.1-SNAPSHOT.jar ${PROJECT_NAME}-0.0.1-SNAPSHOT.jar