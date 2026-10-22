package com.uco.ucopetcommercialprocessor.messages.healthPlan;

import com.uco.ucopetcommercialprocessor.domain.healthPlan.HealthPlanDomain;
import com.uco.ucopetcommercialprocessor.exception.healthPlan.HealthPlanValidationException;
import com.uco.ucopetcommercialprocessor.service.healthPlan.HealthPlanService;
import com.uco.ucopetcommercialprocessor.util.gson.MapperJsonObjeto;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HealthPlanDeactivateConsumer {

    private final HealthPlanService healthPlanService;
    private final MapperJsonObjeto mapper;

    public HealthPlanDeactivateConsumer(HealthPlanService healthPlanService, MapperJsonObjeto mapper) {
        this.healthPlanService = healthPlanService;
        this.mapper = mapper;
    }

    @RabbitListener(queues = "${commercial.healthPlan.deactivate.queue-name}")
    public void consume(String message) {
        HealthPlanDomain healthPlan = mapper.ejecutar(message, HealthPlanDomain.class)
                .orElseThrow(() -> new AmqpRejectAndDontRequeueException(
                        "El mensaje de desactivar plan de salud no es válido"));
        try {
            healthPlanService.deactivate(healthPlan);
        } catch (HealthPlanValidationException ex) {
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
}