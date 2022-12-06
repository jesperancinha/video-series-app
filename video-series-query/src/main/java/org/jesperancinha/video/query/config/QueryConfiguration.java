package org.jesperancinha.video.query.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfiguration {

    @Bean
    public CommandGateway commandGateway() {
        return DefaultCommandGateway.builder()
                .commandBus(AsynchronousCommandBus
                        .builder()
                        .build())
                .build();
    }

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{
                "org.axonframework.**",
                "org.jesperancinha.**"
        });
        return new XStream();
    }

    @Bean
    public Serializer serializer(XStream xStream) {
        XStreamSerializer xStreamSerializer = XStreamSerializer.builder().xStream(xStream).build();
        DefaultConfigurer.defaultConfiguration()
                .configureSerializer(configuration -> xStreamSerializer)
                .configureMessageSerializer(configuration -> xStreamSerializer)
                .configureEventSerializer(configuration -> xStreamSerializer).start();
        return xStreamSerializer;
    }
}
