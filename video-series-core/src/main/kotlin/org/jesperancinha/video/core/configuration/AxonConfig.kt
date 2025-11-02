package org.jesperancinha.video.core.configuration

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.thoughtworks.xstream.converters.collections.CollectionConverter
import com.thoughtworks.xstream.converters.collections.MapConverter
import com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter
import com.thoughtworks.xstream.converters.collections.SingletonMapConverter
import com.thoughtworks.xstream.security.AnyTypePermission
import com.thoughtworks.xstream.security.NoTypePermission
import com.thoughtworks.xstream.security.NullPermission
import com.thoughtworks.xstream.security.PrimitiveTypePermission
import org.axonframework.eventhandling.ReplayToken
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.extensions.mongo.DefaultMongoTemplate
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoTrackingToken
import org.axonframework.messaging.MetaData
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.xml.XStreamSerializer
import org.jesperancinha.video.core.events.AddSeriesEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class AxonConfig(
    @param:Value("\${video.series.mongo.port}")
    val mongoPort: Long,
    @param:Value("\${video.series.mongo.host:localhost}")
    val mongoHost: String
) {

    @Bean
    fun mongoClient(): MongoClient = MongoClients.create("mongodb://${mongoHost}:${mongoPort.toInt()}")

    @Bean
    fun storageEngine(client: MongoClient?, serializer: Serializer): EventStorageEngine {
        val xStream = (serializer as XStreamSerializer).xStream
        xStream.allowTypesByWildcard(arrayOf("org.axonframework.**", "org.jesperancinha.**"))
        xStream.addPermission(NoTypePermission.NONE)
        xStream.addPermission(NullPermission.NULL)
        xStream.addPermission(AnyTypePermission.ANY)
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES)
        xStream.allowTypes(
            arrayOf(
                AddSeriesEvent::class.java,
                MetaData::class.java,
                String::class.java,
                ReplayToken::class.java,
                MongoTrackingToken::class.java,
                Collections::class.java,
                Set::class.java,
                AbstractMap::class.java
            )
        )
        xStream.registerConverter(MapConverter(xStream.mapper))
        xStream.registerConverter(CollectionConverter(xStream.mapper))
        xStream.registerConverter(SingletonCollectionConverter(xStream.mapper))
        xStream.registerConverter(SingletonCollectionConverter(xStream.mapper))
        xStream.registerConverter(SingletonMapConverter(xStream.mapper))
        xStream.allowTypeHierarchy(MutableCollection::class.java)
        xStream.allowTypeHierarchy(Collections::class.java)
        xStream.allowTypeHierarchy(Set::class.java)
        xStream.allowTypeHierarchy(AbstractMap::class.java)
        return MongoEventStorageEngine
            .builder()
            .snapshotSerializer(serializer)
            .eventSerializer(serializer)
            .mongoTemplate(
                DefaultMongoTemplate
                    .builder()
                    .mongoDatabase(client)
                    .build()
            )
            .build()
    }

    @Bean
    fun eventStore(storageEngine: EventStorageEngine?): EmbeddedEventStore =
        EmbeddedEventStore.builder().storageEngine(storageEngine).build()
}