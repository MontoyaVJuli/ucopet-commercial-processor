package com.uco.ucopetcommercialprocessor.config.sale.sale;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(SaleQueueProperties.class)
public class SaleQueueConfig {

    @Bean
    public Declarables saleDeclarables(
            final SaleQueueProperties properties
    ) {
        final List<Declarable> declarables = new ArrayList<>();

        for (SaleQueueProperties.Route route : List.of(
                properties.save(),
                properties.update(),
                properties.deactivate()
        )) {
            final DirectExchange exchange =
                    new DirectExchange(route.exchangeName(), true, false);

            final Queue queue =
                    QueueBuilder.durable(route.queueName()).build();

            declarables.add(exchange);
            declarables.add(queue);
            declarables.add(
                    BindingBuilder.bind(queue)
                            .to(exchange)
                            .with(route.routingKeyName())
            );
        }

        return new Declarables(declarables);
    }
}