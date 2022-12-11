# Video Series Mono Module

## Introduction

This is a monitoring module to quickly identify issues following upgrades

## Test commands

##### Empty payload

```shell
curl -X POST http://localhost:8080/video/series -H "Content-Type: application/json" --data "{}"
```
##### Id 123

```shell
curl -X POST http://localhost:8080/video/series -H "Content-Type: application/json" --data "{\"id\":123}"
```

##### Read history

```shell
curl http://localhost:8080/video/history
```

## References

- [Simple Implementation of Axon 4 with SpringBoot and Mongo DB](https://medium.com/javarevisited/simple-implementation-of-axon-4-with-springboot-and-mongo-db-6ee25008459d)