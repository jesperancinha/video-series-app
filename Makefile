build:
	mvn clean install
test:
	mvn test
local:
	mkdir -p bin
docker:
	docker-compose up -d --build --remove-orphans
build-docker:
	mvn clean install -DskipTests
	docker-compose up -d --build --remove-orphans
no-test:
	mvn clean install -DskipTests