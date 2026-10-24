package com.uco.ucopetcommercialprocessor.config.healthPlan;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(HealthPlanQueueProperties.class)
public class HealthPlanQueueConfig {

    @Bean
    public Declarables healthPlanDeclarables(
            final HealthPlanQueueProperties properties
    ) {
        final List<Declarable> declarables = new ArrayList<>();

        final List<HealthPlanQueueProperties.Route> routes = List.of(
                properties.healthPlan().save(),
                properties.healthPlan().update(),
                properties.healthPlan().deactivate(),
                properties.healthPlanCoverage().save(),
                properties.healthPlanCoverage().update(),
                properties.healthPlanCoverage().deactivate()
        );

        for (final HealthPlanQueueProperties.Route route : routes) {
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
