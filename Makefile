build:
	mvn clean install
test:
	mvn test
local:
	mkdir -p bin
docker:
	docker-compose up
no-test:
	mvn clean install -DskipTests