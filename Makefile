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
docker-databases: stop local
	docker build ./docker-psql/. -t postgres-image
	docker run --name postgres-standalone -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_MULTIPLE_DATABASES=vsa -p 5432:5432 -d postgres-image
docker-mongo: stop local
	docker run --name mongodb-standalone -p 27017:27017 -d mongo
build-images:
	docker build video-series-command/. -t video-series-command
	docker build video-series-query/. -t video-series-query
build-docker: stop no-test
	docker-compose up -d --build --remove-orphans
stop:
	docker-compose down
	docker ps -a -q --filter="name=postgres" | xargs docker stop
	docker ps -a -q --filter="name=postgres" | xargs docker rm
	docker ps -a -q --filter="name=postgres-image" | xargs docker stop
	docker ps -a -q --filter="name=postgres-image" | xargs docker rm
	docker ps -a -q --filter="name=mongo" | xargs docker stop
	docker ps -a -q --filter="name=mongo" | xargs docker rm
	docker ps -a -q --filter="name=video-series-query" | xargs docker stop
	docker ps -a -q --filter="name=video-series-query" | xargs docker rm
	docker ps -a -q --filter="name=video-series-command" | xargs docker stop
	docker ps -a -q --filter="name=video-series-command" | xargs docker rm
dcup-light:
	docker-compose up -d postgres
dcup: dcd
	docker-compose up -d --build --remove-orphans
dcd:
	docker-compose down
