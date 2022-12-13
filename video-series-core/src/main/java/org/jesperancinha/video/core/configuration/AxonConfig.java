package org.jesperancinha.video.core.configuration;

import com.mongodb.MongoClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import lombok.val;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class AxonConfig {
    @Value("${video.series.mongo.port}")
    private Long mongoPort;

    @Value("${video.series.mongo.host:localhost}")
    private String mongoHost;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost, mongoPort.intValue());
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client, Serializer serializer) {
        val xStream = ((XStreamSerializer) serializer).getXStream();
        xStream.allowTypesByWildcard(new String[]{"org.axonframework.**", "org.jesperancinha.**"});
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypes(new Class[]{AddSeriesEvent.class, MetaData.class, String.class});
        xStream.allowTypeHierarchy(Collection.class);
        val defaultMongoTemplate = DefaultMongoTemplate.builder().mongoDatabase(client).build();
        return MongoEventStorageEngine.builder().snapshotSerializer(serializer).eventSerializer(serializer).mongoTemplate(defaultMongoTemplate).build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder().storageEngine(storageEngine).messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore")).build();
    }
}