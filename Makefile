build:
	mvn clean install
test:
	mvn clean install -DskipTests
	docker build video-series-command/. -t video-series-command
	mvn test
local:
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
docker:
	docker-compose up -d --build --remove-orphans
build-docker:
	mvn clean install -DskipTests
	docker-compose up -d --build --remove-orphans
