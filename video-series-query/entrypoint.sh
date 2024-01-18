#!/usr/bin/env sh
java --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED -jar -Dspring.profiles.active=prod video-series-query.jar