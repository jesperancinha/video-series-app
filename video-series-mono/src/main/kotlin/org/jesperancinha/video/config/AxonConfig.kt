package org.jesperancinha.video.config

import com.mongodb.MongoClient
import com.thoughtworks.xstream.security.NoTypePermission
import com.thoughtworks.xstream.security.NullPermission
import com.thoughtworks.xstream.security.PrimitiveTypePermission
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.extensions.mongo.DefaultMongoTemplate
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.messaging.MetaData
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.xml.XStreamSerializer
import org.jesperancinha.video.event.VideoSeriesEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {
    @Value("\${video.series.mongo.port}")
    private val mongoPort: Long? = null
    @Bean
    fun mongoClient(): MongoClient {
        return MongoClient("localhost", mongoPort!!.toInt())
    }

    @Bean
    fun storageEngine(client: MongoClient?, serializer: Serializer): EventStorageEngine {
        val xStream = (serializer as XStreamSerializer).xStream
        xStream.allowTypesByWildcard(
            arrayOf(
                "org.axonframework.**",
                "org.jesperancinha.**"
            )
        )
        xStream.addPermission(NoTypePermission.NONE)
        xStream.addPermission(NullPermission.NULL)
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES)
        xStream.allowTypes(arrayOf(VideoSeriesEvent::class.java, MetaData::class.java, String::class.java))
        xStream.allowTypeHierarchy(MutableCollection::class.java)
        return MongoEventStorageEngine.builder()
            .eventSerializer(serializer)
            .snapshotSerializer(serializer)
            .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build()
    }

    @Bean
    fun eventStore(storageEngine: EventStorageEngine?): EmbeddedEventStore {
        return EmbeddedEventStore.builder().storageEngine(storageEngine).build()
    }
}