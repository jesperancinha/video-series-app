package org.jesperancinha.video.core.configuration

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
import org.jesperancinha.video.core.events.AddSeriesEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {
    @Value("\${video.series.mongo.port}")
    var mongoPort: Long = -1

    @Value("\${video.series.mongo.host:localhost}")
    lateinit var mongoHost: String

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClient(mongoHost, mongoPort.toInt())
    }

    @Bean
    fun storageEngine(client: MongoClient?, serializer: Serializer): EventStorageEngine {
        val xStream = (serializer as XStreamSerializer).xStream
        xStream.allowTypesByWildcard(arrayOf("org.axonframework.**", "org.jesperancinha.**"))
        xStream.addPermission(NoTypePermission.NONE)
        xStream.addPermission(NullPermission.NULL)
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES)
        xStream.allowTypes(arrayOf(AddSeriesEvent::class.java, MetaData::class.java, String::class.java))
        xStream.allowTypeHierarchy(MutableCollection::class.java)
        val defaultMongoTemplate = DefaultMongoTemplate.builder().mongoDatabase(client).build()
        return MongoEventStorageEngine.builder().snapshotSerializer(serializer).eventSerializer(serializer)
            .mongoTemplate(defaultMongoTemplate).build()
    }

    @Bean
    fun eventStore(storageEngine: EventStorageEngine?): EmbeddedEventStore {
        return EmbeddedEventStore.builder().storageEngine(storageEngine).build()
    }
}