b: build
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
docker-clean:
	docker-compose down -v
	docker-compose rm -svf
docker-clean-build-start: docker-clean no-test docker
docker-action:
	docker-compose -f docker-compose.yml up -d --build --remove-orphans
build-images:
	docker build video-series-command/. -t video-series-command
	docker build video-series-query/. -t video-series-query
build-docker: dcd stop no-test
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
pull:
	docker-compose pull
vsa-wait:
	bash vsa_wait.sh
dcup-light:
	docker-compose up -d postgres
dcup: dcd
	docker-compose up -d --build --remove-orphans
	bash vsa_wait.sh
dcd:
	docker-compose down
dcup-full: docker-clean-build-start vsa-wait
dcup-full-action: docker-clean docker-action vsa-wait
cypress-open:
	cd e2e && yarn && npm run cypress
cypress-electron:
	cd e2e && make cypress-electron
cypress-chrome:
	cd e2e && make cypress-chrome
cypress-firefox:
	cd e2e && make cypress-firefox
cypress-edge:
	cd e2e && make cypress-edge
logs-command:
	docker-compose logs -f command
logs-quey:
	docker-compose logs -f query
