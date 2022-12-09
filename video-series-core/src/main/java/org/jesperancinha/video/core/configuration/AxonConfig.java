package org.jesperancinha.video.core.configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collection;

@Configuration
public class AxonConfig {
    @Bean
    public Serializer serializer(XStream xStream) {
        xStream.allowTypesByWildcard(new String[]{
                "org.axonframework.**",
                "org.jesperancinha.**"
        });
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypes(new Class[]{AddSeriesEvent.class});
        xStream.allowTypeHierarchy(Collection.class);
        return XStreamSerializer.builder().xStream(xStream).build();

    }

    @Bean
    public JdbcEventStorageEngine eventStorageEngine(ConnectionProvider connectionProvider, Serializer serializer) {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        configurer.configureSerializer(configuration -> serializer)
                .configureMessageSerializer(configuration -> serializer)
                .configureEventSerializer(configuration -> serializer);

        return JdbcEventStorageEngine
                .builder()
                .connectionProvider(connectionProvider)
                .snapshotSerializer(serializer)
                .transactionManager(NoTransactionManager.INSTANCE)
                .build();
    }

    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }


    @Bean
    public EventStore eventStore(EventStorageEngine eventStorageEngine, Serializer serializer) {
        return AxonServerEventStore
                .builder()
                .snapshotSerializer(serializer)
                .storageEngine(eventStorageEngine)
                .build();
    }
}