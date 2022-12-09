package org.jesperancinha.video.core.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
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
    public Serializer serializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();

    }

    @Bean
    public TokenStore tokenStore(Serializer serializer, XStream xStream) {
        xStream.allowTypesByWildcard(new String[]{
                "org.axonframework.**",
                "org.jesperancinha.**"
        });
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypes(new Class[]{AddSeriesEvent.class});
        xStream.allowTypeHierarchy(Collection.class);
        DefaultConfigurer.defaultConfiguration()
                .configureSerializer(configuration -> serializer)
                .configureMessageSerializer(configuration -> serializer)
                .configureEventSerializer(configuration -> serializer).start();
        return MongoTokenStore.builder().mongoTemplate(axonMongoTemplate()).serializer(serializer).build();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(MongoClient client, Serializer serializer) {
        return MongoEventStorageEngine
                .builder()
                .mongoTemplate(
                        DefaultMongoTemplate
                                .builder()
                                .mongoDatabase(client)
                                .build())
                .eventSerializer(serializer)
                .snapshotSerializer(serializer)
                .build();
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

    @Bean
    public JdbcEventStorageEngine eventStorageEngine(ConnectionProvider connectionProvider) {
        return JdbcEventStorageEngine
                .builder()
                .connectionProvider(connectionProvider)
                .transactionManager(NoTransactionManager.INSTANCE)
                .build();
    }
}