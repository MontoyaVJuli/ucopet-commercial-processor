package com.uco.ucopetcommercialprocessor.messages.healthPlan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uco.ucopetcommercialprocessor.domain.healthPlan.HealthPlanDomain;
import com.uco.ucopetcommercialprocessor.service.healthPlan.HealthPlanService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HealthPlanUpdateConsumer {

    private final HealthPlanService healthPlanService;
    private final ObjectMapper mapper;

    public HealthPlanUpdateConsumer(HealthPlanService healthPlanService) {
        this.healthPlanService = healthPlanService;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RabbitListener(queues = "${commercial.healthPlan.update.queue-name}")
    public void consume(String message) {
        try {
            HealthPlanDomain healthPlan = mapper.readValue(message, HealthPlanDomain.class);
            healthPlanService.save(healthPlan);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException("Error al procesar la actualización del plan de salud: " + ex.getMessage(), ex);
        }
    }
}