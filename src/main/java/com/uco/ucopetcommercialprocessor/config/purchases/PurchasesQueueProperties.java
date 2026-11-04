package com.uco.ucopetcommercialprocessor.config.purchases;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commercial.purchases")
public record PurchasesQueueProperties(
        Route save,
        Route deactivate
) {

    public record Route(
            String exchangeName,
            String queueName,
            String routingKeyName
    ) {}
}
