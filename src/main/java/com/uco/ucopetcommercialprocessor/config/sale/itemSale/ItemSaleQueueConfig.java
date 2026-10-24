package com.uco.ucopetcommercialprocessor.config.sale.itemSale;

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
@EnableConfigurationProperties(ItemSaleQueueProperties.class)
public class ItemSaleQueueConfig {

    @Bean
    public Declarables itemSaleDeclarables(
            final ItemSaleQueueProperties properties
    ) {
        final List<Declarable> declarables = new ArrayList<>();

        for (ItemSaleQueueProperties.Route route : List.of(
                properties.save(),
                properties.update(),
                properties.delete()
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
