#!/usr/bin/env bash
java -jar -Dspring.profiles.active=prod video-series-query.jar --postgres.host=postgres
