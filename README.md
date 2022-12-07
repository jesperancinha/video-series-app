# Video Series App

---

[![Twitter URL](https://img.shields.io/twitter/url?logoColor=blue&style=social&url=https%3A%2F%2Fimg.shields.io%2Ftwitter%2Furl%3Fstyle%3Dsocial)](https://twitter.com/intent/tweet?text=Checkout%20this%20%github%20repo%20by%20%40joaofse%20%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB%3A%20https%3A//github.com/jesperancinha/video-series-app)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitLab&message=Video%20Series%20Apps&color=informational)](https://github.com/jesperancinha/video-series-app)

[![video-series-app](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app.yml/badge.svg)](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app.yml)
[![e2e-video-series-app](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app-e2e.yml/badge.svg)](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app-e2e.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c37d762c19014f54b55ce48771b11453)](https://www.codacy.com/gh/jesperancinha/video-series-app/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/video-series-app&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/c37d762c19014f54b55ce48771b11453)](https://www.codacy.com/gh/jesperancinha/video-series-app/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/video-series-app&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gl/jesperancinha/video-series-app/branch/master/graph/badge.svg)](https://codecov.io/gl/jesperancinha/video-series-app/branch/master)
[![Coverage Status](https://coveralls.io/repos/gitlab/jesperancinha/video-series-app/badge.svg?)](https://coveralls.io/gitlab/jesperancinha/video-series-app?branch=HEAD)


[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/video-series-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/video-series-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/video-series-app.svg)](#)

---

## Technologies used

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/java-50.png "Java")](https://www.oracle.com/nl/java/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/lombok-50.png "Lombok")](https://projectlombok.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kotlin-50.png "Kotlin 1.5.21")](https://kotlinlang.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kotest-50.png "Kotest")](https://kotest.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/mockk-50.png "MockK")](https://mockk.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/jupiter5-50.png "Jupiter 5")](https://junit.org/junit5/docs/current/user-guide/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-50.png "Spring Framework 5.3.9")](https://spring.io/projects/spring-framework)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-boot-50.png "Spring Boot - 2.5.3")](https://spring.io/projects/spring-boot)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-50.png "Docker")](https://www.docker.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-compose-50.png "Docker Compose")](https://docs.docker.com/compose/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/swagger-50.png "Swagger")](https://swagger.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/cypress-50.png "Cypress")](https://www.cypress.io/)

---

## Introduction

> Note: Please have a look at [the log book](./LogBook.md) to keep up with current changes of this project.
> On a quick note, this project is undergoing changes to replace MongoDB and XStream. Issue, [Axon Framework issue 2365](https://github.com/AxonFramework/AxonFramework/issues/2365#event-7412299709), has triggered renovations on this project.

This is an example project to to offer you a very simple example of a CQRS architecture.

This project is also the official support project of my article on medium:

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/swlh/cqrs-command-query-responsibility-segregation-72db08ee8282)
[CQRS — Command Query Responsibility Segregation](https://medium.com/swlh/cqrs-command-query-responsibility-segregation-72db08ee8282)

Please have a look at my [Issues](./Issues.md) file to find out about improvements being made. This file contains a list of ongoing issues that on one hand do not block the development, but on the other hand they are a great nice to haves.

All running issues are being solved in branch [feature/research_and_development](https://github.com/jesperancinha/video-series-app/-/tree/feature/research_and_development).

## Requests

1.  List all

```shell
curl localhost:8090/video-series
```

2.  Create new records

```shell
curl -d '{ "name":"True Blood", "volumes":30, "cashValue": 1323.2, "genre": "DRAMA"}' -H "Content-Type: application/json" -X POST http://localhost:8080/video-series
```

## Swagger

You can also test this application using swagger.

Just run:

```shell
make build-docker dcup
```

And then finally access the API you wish to test:

1.  [Command API](http://localhost:8080/swagger-ui/index.html)
2.  [Query API](http://localhost:8090/swagger-ui/index.html)

Example payload for Command API:

```json
{
  "name": "True Blood",
  "volumes": 30,
  "cashValue": 1323.2,
  "genre": "DRAMA"
}
```

## Installation notes

### Quickstart

The best way to start this application is using the provided docker-composer configuration:

```shell
make build-docker
```

or

```shell
docker-composer up
```

or

```shell
docker composer up
```

There are many further options in the [Makefile](./Makefile).
Please explore them to find the one that suits you best.

In some cases you'll find that there will be a [bin](./bin) folder in the root.
Namely, this will happen when running `make build-databases` or `make local`.
This is where the jars of each application will go to. You can run them accordingly like this:

```shell
java -jar -Dspring.profiles.active=local video-series-command.jar
java -jar -Dspring.profiles.active=local video-series-query.jar
```

Should there be any resource consumption issues running the containers you can try to run:

```shell
make stop
```

This will attempt to stop all running `mongodb` and `postgres` containers, all `video-series-app` containers and all containers in the `docker-compose` file.

## Makefile options in detail

1.  `build` - Builds all java bytecode
2.  `test` - Runs the local tests (they take long in this project)
3.  `local` - Makes a build without tests and places all jars in a /bin folder
4.  `no-test` - Makes a build without tests
5.  `docker` - Starts docker compose
6.  `docker-databases` - Starts the database containers (Postgres + MongoDB) - You then need to start the SpringBoot/jars manually
7.  `docker-mongo` - Only starts the mongodb container - This is meant to be tested with the default profile ONLY. It uses an embedded H2 database instead of Postgres.
8.  `build-images` - Builds the images to run the spring boot processes: `video-series-command` and `video-series-query`
9.  `build-docker` - Makes a clean build, stops containers and relaunches docker-compose.
10. `stop` - Makes a full stop of known containers. It is reused in many of the other commands.

## Profiles

1.  Default - No need to mention the profile in the command line, and it needs MongoDB only. Data is stored in an embedded `H2`
2.  local - Specified with `-Dspring.profiles.active=local`. Needs Postgres and MongoDb. `localhost` is the common host locally
3.  prod - Named after `Production` and runs with `-Dspring.profiles.active=prod`. Only used in docker-compose networks

## Docker helper commands

```shell
docker system prune --all
```

## Analysis commands

```shell
sudo lsof -i :5432
```

## Review Logs

Follow the updates on the [ReviewLogs](./LogBook.md) file.

## References

-   [Working with Postgres Audit Triggers](https://www.enterprisedb.com/postgres-tutorials/working-postgres-audit-triggers)
-   [Apply simplified CQRS and DDD patterns in a microservice](https://docs.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/apply-simplified-microservice-cqrs-ddd-patterns)
-   [Platforms JVM Records](https://kotlinlang.org/docs/jvm-records.html)
-   [Mongo Axon Reference Guide](https://docs.axoniq.io/reference-guide/extensions/mongo)
-   [CQRS Microservice Sampler by Ben Wilcock on GitHub](https://github.com/benwilcock/cqrs-microservice-sampler)
-   [Patrick Gillespie's Text to Ascii Art Generator](http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20)
-   [Microservices With Spring Boot, Axon CQRS/ES, and Docker by Ben Wilcock](https://dzone.com/articles/microservices-with-spring-boot-axon-cqrses-anddock)
-   [Advanced Message Queuing Protocol](https://www.amqp.org/)
-   [Install MongoDB on Mac OS](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
-   [Object-Oriented Software Construction by Bertrand Meyer](https://www.amazon.com/gp/product/0136291554?ie=UTF8&tag=martinfowlerc-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=0136291554)
-   [CQRS Journey by Microsoft](https://docs.microsoft.com/en-gb/previous-versions/msp-n-p/jj554200%28v%3Dpandp.10%29)

## About me 👨🏽‍💻🚀🏳️‍🌈

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/sessionize-20.png "Sessionize")](https://sessionize.com/joao-esperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/@jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bmc-20.png "Buy me a Coffe")](https://www.buymeacoffee.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/credly-20.png "Credly")](https://www.credly.com/users/joao-esperancinha)
[![Generic badge](https://img.shields.io/static/v1.svg?label=WWW&message=joaofilipesabinoesperancinha.nl&color=6495ED "João Esperancinha Homepage")](https://joaofilipesabinoesperancinha.nl/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/google-apps-20.png "Google Apps")](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/sonatype-20.png "Sonatype Search Repos")](https://search.maven.org/search?q=org.jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/docker-20.png "Docker Images")](https://hub.docker.com/u/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/stack-overflow-20.png)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/reddit-20.png "Reddit")](https://www.reddit.com/user/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/devto-20.png "Dev To")](https://dev.to/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackernoon-20.jpeg "Hackernoon")](https://hackernoon.com/@jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeproject-20.png "Code Project")](https://www.codeproject.com/Members/jesperancinha)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=social "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bitbucket-20.png "BitBucket")](https://bitbucket.org/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/gitlab-20.png "GitLab")](https://gitlab.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/coursera-20.png "Coursera")](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/free-code-camp-20.jpg "FreeCodeCamp")](https://www.freecodecamp.org/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackerrank-20.png "HackerRank")](https://www.hackerrank.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/leet-code-20.png "LeetCode")](https://leetcode.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codebyte-20.png "Codebyte")](https://coderbyte.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codewars-20.png "CodeWars")](https://www.codewars.com/users/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codepen-20.png "Code Pen")](https://codepen.io/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hacker-earth-20.png "Hacker Earth")](https://www.hackerearth.com/@jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/khan-academy-20.png "Khan Academy")](https://www.khanacademy.org/profile/jofisaes)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social "Twitter")](https://twitter.com/joaofse)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/mastodon-20.png "Mastodon")](https://masto.ai/@jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hacker-news-20.png "Hacker News")](https://news.ycombinator.com/user?id=jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/infoq-20.png "InfoQ")](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/linkedin-20.png "LinkedIn")](https://www.linkedin.com/in/joaoesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/xing-20.png "Xing")](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/tumblr-20.png "Tumblr")](https://jofisaes.tumblr.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/pinterest-20.png "Pinterest")](https://nl.pinterest.com/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/quora-20.png "Quora")](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)
[![VMware Spring Professional 2021](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/vmware-spring-professional-2021-20.png "VMware Spring Professional 2021")](https://www.credly.com/badges/762fa7a4-9cf4-417d-bd29-7e072d74cdb7)
[![Oracle Certified Professional, JEE 7 Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-ee-7-application-developer-20.png "Oracle Certified Professional, JEE7 Developer")](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![Oracle Certified Professional, Java SE 11 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-11-developer-20.png "Oracle Certified Professional, Java SE 11 Programmer")](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![IBM Cybersecurity Analyst Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/ibm-cybersecurity-analyst-professional-certificate-20.png "IBM Cybersecurity Analyst Professional")](https://www.credly.com/badges/ad1f4abe-3dfa-4a8c-b3c7-bae4669ad8ce)
[![Certified Advanced JavaScript Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/cancanit-badge-1462-20.png "Certified Advanced JavaScript Developer")](https://cancanit.com/certified/1462/)
[![Certified Neo4j Professional](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/professional_neo4j_developer-20.png "Certified Neo4j Professional")](https://graphacademy.neo4j.com/certificates/c279afd7c3988bd727f8b3acb44b87f7504f940aac952495ff827dbfcac024fb.pdf)
[![Deep Learning](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/deep-learning-20.png "Deep Learning")](https://www.credly.com/badges/8d27e38c-869d-4815-8df3-13762c642d64)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=JEsperancinhaOrg&color=yellow "jesperancinha.org dependencies")](https://github.com/JEsperancinhaOrg)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red "All badges")](https://joaofilipesabinoesperancinha.nl/badges)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red "Project statuses")](https://github.com/jesperancinha/project-signer/blob/master/project-signer-quality/Build.md)
