package org.jesperancinha.video.core.configuration;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database:test}")
    private String mongoDatabase;

    @Bean
    public JdbcEventStorageEngine eventStorageEngine(ConnectionProvider connectionProvider) {
        return JdbcEventStorageEngine
                .builder()
                .connectionProvider(connectionProvider)
                .transactionManager(NoTransactionManager.INSTANCE)
                .build();
    }
}