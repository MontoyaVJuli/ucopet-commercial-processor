package com.uco.ucopetcommercialprocessor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderRabbitConfig {

    @Bean
    public DirectExchange providerSaveExchange(CommercialQueueProperties properties) {
        return new DirectExchange(properties.provider().save().exchangeName());
    }
    @Bean
    public Queue providerSaveQueue(CommercialQueueProperties properties) {
        return new Queue(properties.provider().save().queueName(), true);
    }
    @Bean
    public Binding providerSaveBinding(Queue providerSaveQueue, DirectExchange providerSaveExchange,
                                       CommercialQueueProperties properties) {
        return BindingBuilder.bind(providerSaveQueue)
                .to(providerSaveExchange)
                .with(properties.provider().save().routingKeyName());
    }
    @Bean
    public DirectExchange providerDeactivateExchange(CommercialQueueProperties properties) {
        return new DirectExchange(properties.provider().deactivate().exchangeName());
    }
    @Bean
    public Queue providerDeactivateQueue(CommercialQueueProperties properties) {
        return new Queue(properties.provider().deactivate().queueName(), true);
    }
    @Bean
    public Binding providerDeactivateBinding(Queue providerDeactivateQueue,
                                             DirectExchange providerDeactivateExchange,
                                             CommercialQueueProperties properties) {
        return BindingBuilder.bind(providerDeactivateQueue)
                .to(providerDeactivateExchange)
                .with(properties.provider().deactivate().routingKeyName());
    }
}
