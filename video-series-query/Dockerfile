FROM openjdk:17-slim-buster

ENV runningFolder /usr/local/bin/

WORKDIR ${runningFolder}

RUN apt-get update

COPY target/video-series-query.jar ${runningFolder}/video-series-query.jar

COPY entrypoint.sh ${runningFolder}

EXPOSE 8090

ENTRYPOINT ["entrypoint.sh"]
