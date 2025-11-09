FROM eclipse-temurin:21-alpine

ENV runningFolder=/usr/local/bin/

WORKDIR ${runningFolder}

RUN apk --no-cache add curl

COPY target/video-series-query.jar ${runningFolder}/video-series-query.jar

COPY entrypoint.sh ${runningFolder}

EXPOSE 8090

ENTRYPOINT ["entrypoint.sh"]
