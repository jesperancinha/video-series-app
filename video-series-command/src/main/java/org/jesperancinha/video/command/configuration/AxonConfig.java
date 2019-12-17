package org.jesperancinha.video.command.configuration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
public class AxonConfig {

    @Value("${mongo.host:127.0.0.1}")
    private String mongoHost;

    @Value("${mongo.port:27017}")
    private int mongoPort;

    @Value("${mongo.db:test}")
    private String mongoDB;
//
//    @Bean
//    public MongoSagaStore sagaStore() {
//        return MongoSagaStore.builder().mongoTemplate(axonMongoTemplate()).build();
//    }

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
        return DefaultMongoTemplate.builder().mongoDatabase(mongo()).domainEventsCollectionName(mongoDB).build();
    }
//
//    @Bean
//    public Configurer configurer() {
//        return DefaultConfigurer.defaultConfiguration()
//                .configureEmbeddedEventStore(
//                        c -> MongoEventStorageEngine.builder().mongoTemplate(axonMongoTemplate()).build()
//                );
//    }

    @Bean
    public MongoClient mongo() {
        MongoFactory mongoFactory = new MongoFactory();
        mongoFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost, mongoPort)));
        return mongoFactory.createMongo();
    }
}