SHELL := /bin/bash
GITHUB_RUN_ID ?=123

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
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose-db.yml -f docker-compose.yml -f docker-compose.override.yml up -d --build --remove-orphans
docker-databases: stop local
	docker build ./docker-psql/. -t postgres-image
	docker run --name postgres-standalone -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_MULTIPLE_DATABASES=vsa -p 5432:5432 -d postgres-image
docker-mongo: stop local
	docker run --name mongodb-standalone -p 27017:27017 -d mongo
docker-clean:
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml down -v
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml rm -svf
docker-clean-build-start: docker-clean stop no-test docker
docker-action:
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml up -d --build --remove-orphans
docker-stop-all:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker stop {}
docker-remove-all:
	docker ps -a --format '{{.ID}}' | xargs -I {}  docker rm {}
build-images:
	docker build video-series-command/. -t video-series-command
	docker build video-series-query/. -t video-series-query
build-docker: dcd stop no-test
	docker-compose -p ${GITHUB_RUN_ID} up -d --build --remove-orphans
stop:
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml down
	docker ps -a -q --filter="name=postgres" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=postgres" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=postgres-image" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=postgres-image" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=mongo" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=mongo" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=video-series-query" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=video-series-query" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=video-series-command" | xargs -I {} docker stop {}
	docker ps -a -q --filter="name=video-series-command" | xargs -I {} docker stop {}
pull:
	docker-compose -p ${GITHUB_RUN_ID} pull
vsa-wait:
	bash vsa_wait.sh
dcup-light:
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml -f docker-compose.override.yml up -d postgres mongo
dcup: dcd
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml -f docker-compose.override.yml up -d --build --remove-orphans
	bash vsa_wait.sh
dcd:
	docker-compose -p ${GITHUB_RUN_ID} -f docker-compose.yml -f docker-compose-db.yml down
dcup-full: docker-clean-build-start vsa-wait
dcup-full-action: docker-clean no-test docker-action vsa-wait
cypress-open:
	cd e2e && yarn && npm run cypress:open:electron
cypress-electron:
	cd e2e && make cypress-electron
cypress-chrome:
	cd e2e && make cypress-chrome
cypress-firefox:
	cd e2e && make cypress-firefox
cypress-edge:
	cd e2e && make cypress-edge
logs-command:
	docker-compose -p ${GITHUB_RUN_ID} logs -f command
logs-quey:
	docker-compose -p ${GITHUB_RUN_ID} logs -f query
install:
	sudo npm install -g npm-check-updates
version-status:
	mvn versions:display-dependency-updates
version-update-maven:
	mvn versions:use-next-releases
	mvn versions:use-latest-releases
	mvn versions:use-releases
update:
	find . -name "package-lock.json" | xargs rm; \
	find . -name "yarn.lock" | xargs rm; \
	git pull; \
	curl --compressed -o- -L https://yarnpkg.com/install.sh | bash; \
	npm install caniuse-lite; \
	npm install -g npm-check-updates; \
	cd e2e; \
		yarn; \
		npx browserslist --update-db; \
		ncu -u; \
		yarn
deps-update: update
revert-deps-cypress-update:
	if [ -f  e2e/docker-composetmp.yml ]; then rm e2e/docker-composetmp.yml; fi
	if [ -f  e2e/packagetmp.json ]; then rm e2e/packagetmp.json; fi
	git checkout e2e/docker-compose.yml
	git checkout e2e/package.json
deps-cypress-update:
	curl -sL https://raw.githubusercontent.com/jesperancinha/project-signer/master/cypressUpdateOne.sh | bash
deps-plugins-update:
	curl -sL https://raw.githubusercontent.com/jesperancinha/project-signer/master/pluginUpdatesOne.sh | bash
deps-quick-update: deps-cypress-update deps-plugins-update
accept-prs:
	curl -sL https://raw.githubusercontent.com/jesperancinha/project-signer/master/acceptPR.sh | bash
