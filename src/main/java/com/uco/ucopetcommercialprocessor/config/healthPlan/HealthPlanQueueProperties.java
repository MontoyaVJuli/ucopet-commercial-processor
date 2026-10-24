package com.uco.ucopetcommercialprocessor.config.healthPlan;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commercial")
public record HealthPlanQueueProperties(
        HealthPlan healthPlan,
        HealthPlanCoverage healthPlanCoverage
) {

    public record HealthPlan(
            Route save,
            Route update,
            Route deactivate
    ) {}

    public record HealthPlanCoverage(
            Route save,
            Route update,
            Route deactivate
    ) {}

    public record Route(
            String exchangeName,
            String queueName,
            String routingKeyName
    ) {}
}
