FROM openjdk:11-jre-slim
WORKDIR /app

ARG API_VERSION="latest"
ENV API_VERSION ${API_VERSION}
ENV PROJECT_NAME="reno"

COPY target/reno-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","reno-0.0.1-SNAPSHOT.jar"]


RUN mkdir -p BOOT-INF/classes/
ADD src/main/resources/application-template.properties BOOT-INF/classes/

WORKDIR /app/src/main/resources


ADD ./run.sh ./run.sh
RUN sed -i 's/\r$//' run.sh
RUN chmod +x 'run.sh'