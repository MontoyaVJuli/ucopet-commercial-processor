package com.uco.ucopetcommercialprocessor.config.sale.sale;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commercial.sale")
public record SaleQueueProperties(
        Route save,
        Route update,
        Route deactivate
) {
    public record Route(
            String exchangeName,
            String queueName,
            String routingKeyName
    ) {
    }
}
