all:
	mvn clean install | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
	docker build video-series-command/. -t video-series-command
	docker build video-series-query/. -t video-series-query
build:
	mvn clean install | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
test:
	mvn test | grep -v "com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.wire"
local:
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
docker:
	docker-compose up -d --build --remove-orphans
build-docker:
	mvn clean install -DskipTests
	docker-compose up -d --build --remove-orphans
