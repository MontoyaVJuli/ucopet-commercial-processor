package com.uco.ucopetcommercialprocessor.messages.healthPlanCoverage;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uco.ucopetcommercialprocessor.domain.healthPlanCoverage.HealthPlanCoverageDomain;
import com.uco.ucopetcommercialprocessor.service.healthPlanCoverage.HealthPlanCoverageService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HealthPlanCoverageSaveConsumer {

    private final HealthPlanCoverageService healthPlanCoverageService;
    private final ObjectMapper mapper;

    public HealthPlanCoverageSaveConsumer(HealthPlanCoverageService healthPlanCoverageService) {
        this.healthPlanCoverageService = healthPlanCoverageService;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RabbitListener(queues = "${commercial.health-plan-coverage.save.queue-name}")
    public void consume(String message) {
        try {
            HealthPlanCoverageDomain coverage = mapper.readValue(message, HealthPlanCoverageDomain.class);
            healthPlanCoverageService.save(coverage);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException("Error al procesar la cobertura: " + ex.getMessage(), ex);
        }
    }
}