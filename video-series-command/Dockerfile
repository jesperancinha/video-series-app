FROM openjdk:20-slim-buster

ENV runningFolder /usr/local/bin/

WORKDIR ${runningFolder}

RUN apt-get update

COPY target/video-series-command.jar ${runningFolder}/video-series-command.jar

COPY entrypoint.sh ${runningFolder}

EXPOSE 8080

ENTRYPOINT ["entrypoint.sh"]
