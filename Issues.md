## VSA Issue Tracker

1. Records between Command and Query

```java
Hibernate: select videoserie0_.id as id1_3_, videoserie0_.cash_value as cash_val2_3_, videoserie0_.genre as genre3_3_, videoserie0_.name as name4_3_, videoserie0_.volumes as volumes5_3_ from video_series videoserie0_
2021-08-19 11:44:16.459  INFO 35801 --- [video-series]-0] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:4, serverValue:9}] to localhost:54227
Security framework of XStream not explicitly initialized, using predefined black list on your own risk.
2021-08-19 11:44:17.527 ERROR 35801 --- [video-series]-0] o.a.eventhandling.LoggingErrorHandler    : EventListener [VideoSeriesEventHandler$$EnhancerBySpringCGLIB$$a208e839] failed to handle event [192bec3e-bd46-4d5c-899c-5f4bd24b5f2f] (org.jesperancinha.video.core.events.AddSeriesEvent). Continuing processing with next listener

com.thoughtworks.xstream.converters.ConversionException: 
---- Debugging information ----
cause-exception     : java.lang.UnsupportedOperationException
cause-message       : can't get field offset on a record class: private final java.lang.String org.jesperancinha.video.core.events.AddSeriesEvent.id
class               : org.jesperancinha.video.core.events.AddSeriesEvent
required-type       : org.jesperancinha.video.core.events.AddSeriesEvent
converter-type      : com.thoughtworks.xstream.converters.reflection.ReflectionConverter
path                : /org.jesperancinha.video.core.events.AddSeriesEvent/id
line number         : 1
version             : 1.4.17
-------------------------------
	at com.thoughtworks.xstream.core.TreeUnmarshaller.convert(TreeUnmarshaller.java:77) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller.convert(AbstractReferenceUnmarshaller.java:72) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.TreeUnmarshaller.convertAnother(TreeUnmarshaller.java:66) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.TreeUnmarshaller.convertAnother(TreeUnmarshaller.java:50) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.TreeUnmarshaller.start(TreeUnmarshaller.java:134) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.AbstractTreeMarshallingStrategy.unmarshal(AbstractTreeMarshallingStrategy.java:32) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.XStream.unmarshal(XStream.java:1431) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.XStream.unmarshal(XStream.java:1411) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.XStream.fromXML(XStream.java:1296) ~[xstream-1.4.17.jar:1.4.17]
	at org.axonframework.serialization.xml.XStreamSerializer.doDeserialize(XStreamSerializer.java:123) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.serialization.AbstractXStreamSerializer.deserialize(AbstractXStreamSerializer.java:151) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.serialization.LazyDeserializingObject.getObject(LazyDeserializingObject.java:102) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.serialization.SerializedMessage.getPayload(SerializedMessage.java:77) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.MessageDecorator.getPayload(MessageDecorator.java:56) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.annotation.PayloadParameterResolver.resolveParameterValue(PayloadParameterResolver.java:41) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.annotation.AnnotatedMessageHandlingMember.resolveParameterValues(AnnotatedMessageHandlingMember.java:168) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.annotation.AnnotatedMessageHandlingMember.handle(AnnotatedMessageHandlingMember.java:144) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.annotation.AnnotatedHandlerInspector$NoMoreInterceptors.handle(AnnotatedHandlerInspector.java:372) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.AnnotationEventHandlerAdapter.handle(AnnotationEventHandlerAdapter.java:94) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.SimpleEventHandlerInvoker.handle(SimpleEventHandlerInvoker.java:112) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.MultiEventHandlerInvoker.handle(MultiEventHandlerInvoker.java:89) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.AbstractEventProcessor.lambda$null$1(AbstractEventProcessor.java:165) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.DefaultInterceptorChain.proceed(DefaultInterceptorChain.java:57) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.interceptors.CorrelationDataInterceptor.handle(CorrelationDataInterceptor.java:65) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.DefaultInterceptorChain.proceed(DefaultInterceptorChain.java:55) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.TrackingEventProcessor.lambda$new$1(TrackingEventProcessor.java:185) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.DefaultInterceptorChain.proceed(DefaultInterceptorChain.java:55) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.AbstractEventProcessor.lambda$processInUnitOfWork$2(AbstractEventProcessor.java:173) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.messaging.unitofwork.BatchingUnitOfWork.executeWithResult(BatchingUnitOfWork.java:86) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.AbstractEventProcessor.processInUnitOfWork(AbstractEventProcessor.java:159) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.TrackingEventProcessor.processBatch(TrackingEventProcessor.java:451) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.TrackingEventProcessor.processingLoop(TrackingEventProcessor.java:294) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.TrackingEventProcessor$TrackingSegmentWorker.run(TrackingEventProcessor.java:1005) ~[axon-messaging-4.5.3.jar:4.5.3]
	at org.axonframework.eventhandling.TrackingEventProcessor$WorkerLauncher.run(TrackingEventProcessor.java:1149) ~[axon-messaging-4.5.3.jar:4.5.3]
	at java.base/java.lang.Thread.run(Thread.java:831) ~[na:na]
Caused by: java.lang.UnsupportedOperationException: can't get field offset on a record class: private final java.lang.String org.jesperancinha.video.core.events.AddSeriesEvent.id
	at jdk.unsupported/sun.misc.Unsafe.objectFieldOffset(Unsafe.java:648) ~[na:na]
	at com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider.getFieldOffset(SunUnsafeReflectionProvider.java:105) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider.write(SunUnsafeReflectionProvider.java:66) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider.writeField(SunUnsafeReflectionProvider.java:56) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter.doUnmarshal(AbstractReflectionConverter.java:456) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter.unmarshal(AbstractReflectionConverter.java:277) ~[xstream-1.4.17.jar:1.4.17]
	at com.thoughtworks.xstream.core.TreeUnmarshaller.convert(TreeUnmarshaller.java:72) ~[xstream-1.4.17.jar:1.4.17]
	... 34 common frames omitted

Hibernate: select videoserie0_.id as id1_3_, videoserie0_.cash_value as cash_val2_3_, videoserie0_.genre as genre3_3_, videoserie0_.name as name4_3_, videoserie0_.volumes as volumes5_3_ from video_series videoserie0_
Hibernate: select videoserie0_.id as id1_3_, videoserie0_.cash_value as cash_val2_3_, videoserie0_.genre as genre3_3_, videoserie0_.name as name4_3_, videoserie0_.volumes as volumes5_3_ from video_series videoserie0_



2021-08-19 11:44:21.873  INFO 35801 --- [ionShutdownHook] o.a.e.TrackingEventProcessor             : Shutdown state set for Processor 'video-series'.
2021-08-19 11:44:21.874  INFO 35801 --- [nPool-worker-19] o.a.e.TrackingEventProcessor             : Processor 'video-series' awaiting termination...
2021-08-19 11:44:22.600  INFO 35801 --- [video-series]-0] o.a.e.TrackingEventProcessor             : Released claim
2021-08-19 11:44:22.600  INFO 35801 --- [video-series]-0] o.a.e.TrackingEventProcessor             : Worker for segment Segment[0/0] stopped.
2021-08-19 11:44:22.606  INFO 35801 --- [ionShutdownHook] org.mongodb.driver.connection            : Closed connection [connectionId{localValue:3, serverValue:8}] to localhost:54227 because the pool has been closed.
2021-08-19 11:44:22.607  INFO 35801 --- [ionShutdownHook] org.mongodb.driver.connection            : Closed connection [connectionId{localValue:4, serverValue:9}] to localhost:54227 because the pool has been closed.
2021-08-19 11:44:22.611  INFO 35801 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2021-08-19 11:44:22.611  INFO 35801 --- [ionShutdownHook] .SchemaDropperImpl$DelayedDropActionImpl : HHH000477: Starting delayed evictData of schema as part of SessionFactory shut-down'
Hibernate: drop table if exists association_value_entry cascade
Hibernate: drop table if exists saga_entry cascade
Hibernate: drop table if exists token_entry cascade
Hibernate: drop table if exists video_series cascade
Hibernate: drop sequence if exists hibernate_sequence
2021-08-19 11:44:22.630  INFO 35801 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2021-08-19 11:44:22.632  INFO 35801 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 255

Collection should not be empty
java.lang.AssertionError: Collection should not be empty
	at org.jesperancinha.video.query.rest.VideoSeriesQueryControllerITTest$1$1$1.invokeSuspend(VideoSeriesQueryControllerITTest.kt:100)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
	at java.base/java.lang.Thread.run(Thread.java:831)
```