package com.uco.ucopetcommercialprocessor.config.sale.itemSale;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commercial.sale-item")
public record ItemSaleQueueProperties(
        Route save,
        Route update,
        Route delete
) {
    public record Route(
            String exchangeName,
            String queueName,
            String routingKeyName
    ) {}
}