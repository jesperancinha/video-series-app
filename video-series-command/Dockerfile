FROM adoptopenjdk/openjdk16

ENV runningFolder /usr/local/bin/

WORKDIR ${runningFolder}

RUN apt-get update

COPY ../bin/video-series-command*.jar ${runningFolder}/video-series-command.jar

COPY entrypoint.sh ${runningFolder}

EXPOSE 8080

ENTRYPOINT ["entrypoint.sh"]
