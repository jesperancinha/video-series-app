FROM eclipse-temurin:21-alpine

ENV runningFolder=/usr/local/bin/

WORKDIR ${runningFolder}

RUN apk --no-cache add curl

COPY target/video-series-command.jar ${runningFolder}/video-series-command.jar

COPY entrypoint.sh ${runningFolder}

EXPOSE 8080

ENTRYPOINT ["entrypoint.sh"]
