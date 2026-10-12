package com.uco.ucopetcommercialprocessor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * TEMPORAL: al arrancar imprime las 12 colas cargadas de Commercial.properties,
 * para comprobar que el binding funciona. Borrar cuando existan los consumidores.
 */

@Component
public class CommercialPropertiesCheck implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CommercialPropertiesCheck.class);

    private final CommercialQueueProperties properties;

    public CommercialPropertiesCheck(CommercialQueueProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) {
        show("petcare", properties.sale());
        show("order", properties.provider());
        show("appointment", properties.purchases());
        show("specialty", properties.healthPlan());
    }

    private void show(String module, CommercialQueueProperties.ModuleQueues queues) {
        show(module + ".save", queues.save());
        show(module + ".deactivate", queues.deactivate());
    }

    private void show(String name, CommercialQueueProperties.Queue queue) {
        log.info("[COMMERCIAL] {} -> exchange={} queue={} routingKey={}",
                name, queue.exchangeName(), queue.queueName(), queue.routingKeyName());
    }
}
