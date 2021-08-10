package org.jesperancinha.video.core.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AxonConfig {

    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;

    @Value("${spring.data.mongodb.database:test}")
    private String mongoDatabase;

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder().mongoTemplate(axonMongoTemplate()).serializer(serializer).build();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder().mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder().mongoDatabase(mongo(), mongoDatabase).build();
    }

    @Bean
    public MongoClient mongo() {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(List.of(
                                        new ServerAddress(mongoHost, mongoPort))))
                        .build());
    }
}