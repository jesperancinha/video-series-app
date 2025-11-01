# Video Series App

---


[![Generic badge](https://img.shields.io/static/v1.svg?label=GitLab&message=Video%20Series%20Apps&color=informational)](https://github.com/jesperancinha/video-series-app)

[![video-series-app](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app.yml/badge.svg)](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app.yml)
[![e2e-video-series-app](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app-e2e.yml/badge.svg)](https://github.com/jesperancinha/video-series-app/actions/workflows/video-series-app-e2e.yml)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c37d762c19014f54b55ce48771b11453)](https://www.codacy.com/gh/jesperancinha/video-series-app/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/video-series-app&amp;utm_campaign=Badge_Grade)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/c37d762c19014f54b55ce48771b11453)](https://www.codacy.com/gh/jesperancinha/video-series-app/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jesperancinha/video-series-app&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gh/jesperancinha/video-series-app/branch/master/graph/badge.svg)](https://codecov.io/gh/jesperancinha/video-series-app/branch/master)
[![Coverage Status](https://coveralls.io/repos/github/jesperancinha/video-series-app/badge.svg?branch=master)](https://coveralls.io/github/jesperancinha/video-series-app?branch=master)

[![GitHub language count](https://img.shields.io/github/languages/count/jesperancinha/video-series-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/jesperancinha/video-series-app.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/code-size/jesperancinha/video-series-app.svg)](#)

---

## Technologies used

Please check the [TechStack.md](TechStack.md) file for details.

## Introduction

This is an example project to to offer you a very simple example of a CQRS architecture.

This project is also the official support project of my article on dev.to:

[![](https://img.shields.io/badge/CQRS%20—%20Command%20Query%20Responsibility%20Segregation-12100E?style=for-the-badge&logo=dev.to&logoColor=white)](https://dev.to/jofisaes/cqrs-command-query-responsibility-segregation-a-java-spring-springboot-and-axon-example-4i26)

Please have a look at my [Issues](./Issues.md) file to find out about improvements being made. This file contains a list
of ongoing issues that on one hand do not block the development, but on the other hand they are a great nice to haves.

#### Stable releases

-   [1.0.0](https://github.com/jesperancinha/video-series-app/tree/1.0.0) - [10271cee0b49da1cb80bdcd115851de94f928774](https://github.com/jesperancinha/video-series-app/tree/1.0.0) - Java
-   [2.0.0](https://github.com/jesperancinha/video-series-app/tree/2.0.0) - [98eb81756c02d63d03c19e8ae87fc8023d513e0a](https://github.com/jesperancinha/video-series-app/tree/2.0.0) - Java
-   [3.0.0](https://github.com/jesperancinha/video-series-app/tree/3.0.0) - [696472601c04a6a26c01e83fcf464eafee4305f4](https://github.com/jesperancinha/video-series-app/tree/3.0.0) - Java
-   [3.0.1](https://github.com/jesperancinha/video-series-app/tree/3.0.1) - [022a709a1fe65b57c89c68974d6577d029039eb2](https://github.com/jesperancinha/video-series-app/tree/3.0.1) - Java / Refactoring Maven (Known working dependencies)
-   [3.0.2](https://github.com/jesperancinha/video-series-app/tree/3.0.2) - [3a922f4a64c6602566b3c81f4811c40cc2c119e0](https://github.com/jesperancinha/video-series-app/tree/3.0.2) - Java / Spring Boot 2.6.14 Update
-   [3.0.3](https://github.com/jesperancinha/video-series-app/tree/3.0.3) - [9f53922e9be7e1a110ac9059a556a9a9571a1921](https://github.com/jesperancinha/video-series-app/tree/3.0.3) - Java / Spring Boot 2.7.6 Update, JDK19 and mono module converted to Kotlin
-   [3.0.4](https://github.com/jesperancinha/video-series-app/tree/3.0.4) - [ba5104f6593b7e8001f5302904b0a5f14a5697c4](https://github.com/jesperancinha/video-series-app/tree/3.0.4) - Java / Spring Boot 3.0.2 Update
-   [4.0.0](https://github.com/jesperancinha/video-series-app/tree/4.0.0) - [15f2abfa5e17dc881e66b2761f5a2672fd87005a](https://github.com/jesperancinha/video-series-app/tree/4.0.0) - Full Kotlin 1.8.0 / JDK19

## Requests

1.  List all

```shell
curl localhost:8090/video-series
```

2.  Create new records

```shell
curl -d '{ "name":"MacGyver", "volumes":233, "cashValue": 9999.9, "genre": "ACTION"}' -H "Content-Type: application/json" -X POST http://localhost:8080/video-series
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
  "name": "MacGyver",
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

This will attempt to stop all running `mongodb` and `postgres` containers, all `video-series-app` containers and all
containers in the `docker-compose` file.

## Makefile options in detail

1.  `build` - Builds all java bytecode
2.  `test` - Runs the local tests (they take long in this project)
3.  `local` - Makes a build without tests and places all jars in a /bin folder
4.  `no-test` - Makes a build without tests
5.  `docker` - Starts docker compose
6.  `docker-databases` - Starts the database containers (Postgres + MongoDB) - You then need to start the SpringBoot/jars
   manually
7.  `docker-mongo` - Only starts the mongodb container - This is meant to be tested with the default profile ONLY. It
   uses an embedded H2 database instead of Postgres.
8.  `build-images` - Builds the images to run the spring boot processes: `video-series-command` and `video-series-query`
9.  `build-docker` - Makes a clean build, stops containers and relaunches docker-compose.
10. `stop` - Makes a full stop of known containers. It is reused in many of the other commands.

## Profiles

1.  Default - No need to mention the profile in the command line, and it needs MongoDB only. Data is stored in an
   embedded `H2`
2.  local - Specified with `-Dspring.profiles.active=local`. Needs Postgres and MongoDb. `localhost` is the common host
   locally
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

## Working versions

##### 1.0.0

```shell
git checkout 1.0.0
```

##### 2.0.0

```shell
git checkout 2.0.0
```

## Checklist

* For every image update, file [VideoSeriesQueryControllerITTest](video-series-query/src/test/kotlin/org/jesperancinha/video/query/rest/VideoSeriesQueryControllerITTest.kt) must also be updated.

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

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
