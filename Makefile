all:
	mvn clean install | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
build:
	mvn clean install | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
test:
	mvn test | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
local: no-test
	mkdir -p bin
	cp video-series-command/target/video-series-command*.jar bin/video-series-command.jar
	cp video-series-query/target/video-series-query*.jar bin/video-series-query.jar
no-test:
	mvn clean install -DskipTests
docker:
	docker-compose up -d --build --remove-orphans
build-images:
	docker build video-series-command/. -t video-series-command
	docker build video-series-query/. -t video-series-query
build-docker: no-test
	docker-compose down
	docker-compose up -d --build --remove-orphans
