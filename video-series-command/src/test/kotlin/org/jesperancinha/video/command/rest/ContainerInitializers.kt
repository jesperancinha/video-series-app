package org.jesperancinha.video.command.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class VideoSeriesCommandInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        logger.info("Starting IT -> ${LocalDateTime.now()}")
        val postgres1Host = dockerCompose.getServiceHost("postgres_1", 5432)
        val postgres1Port = dockerCompose.getServicePort("postgres_1", 5432)
        logger.info("Starting service 1 at $postgres1Host")
        logger.info("Starting service 1 at ${dockerCompose.getServiceHost("postgres-es_1", 5432)}")
        logger.info("End IT -> ${LocalDateTime.now()}")
        logger.info("Time Elapsed IT -> ${ChronoUnit.MILLIS.between(startup, LocalDateTime.now())} ms")
        val jdbcConnection1 = "jdbc:postgresql://$postgres1Host:$postgres1Port/vsa"
        logger.info("JDBC connection 1 -> $jdbcConnection1")
        val mongoPort: Int = dockerCompose.getServicePort(
            "mongo_1",
            27017
        )
        TestPropertySourceUtils
            .addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=$jdbcConnection1",
                "spring.datasource.username=postgres",
                "spring.datasource.password=admin",
                "video.series.mongo.port=$mongoPort"
            )
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(VideoSeriesCommandInitializer::class.java)
        private val startup: LocalDateTime = LocalDateTime.now()

        private val dockerCompose by lazy {
            DockerCompose(listOf(File("../docker-compose-db.yml")))
                .withExposedService("postgres_1", 5432, Wait.forListeningPort())
                .withExposedService("mongo_1", 27017, Wait.forListeningPort())
                .withLocalCompose(true)
                .also { it.start() }
        }

    }
}

private class DockerCompose(files: List<File>) : DockerComposeContainer<DockerCompose>(files)