package org.jesperancinha.video.query.config;

import org.axonframework.queryhandling.DefaultQueryGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfiguration {

    @Bean
    public QueryGateway queryGateway() {
        return DefaultQueryGateway.builder()
                .queryBus(SimpleQueryBus
                        .builder()
                        .build())
                .build();
    }

}
