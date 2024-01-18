package org.jesperancinha.video.core.configuration

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
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
    @Value("\${video.series.mongo.port}")
    val mongoPort: Long,
    @Value("\${video.series.mongo.host:localhost}")
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
            )
        )
        xStream.allowTypeHierarchy(MutableCollection::class.java)
        xStream.allowTypeHierarchy(Collections::class.java)
        val defaultMongoTemplate = DefaultMongoTemplate.builder().mongoDatabase(client).build()
        return MongoEventStorageEngine.builder().snapshotSerializer(serializer).eventSerializer(serializer)
            .mongoTemplate(defaultMongoTemplate).build()
    }

    @Bean
    fun eventStore(storageEngine: EventStorageEngine?): EmbeddedEventStore =
        EmbeddedEventStore.builder().storageEngine(storageEngine).build()
}