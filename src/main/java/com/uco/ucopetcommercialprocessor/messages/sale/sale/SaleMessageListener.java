package com.uco.ucopetcommercialprocessor.messages.sale.sale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class SaleMessageListener {

    private static final Logger log =
            LoggerFactory.getLogger(SaleMessageListener.class);

    @RabbitListener(queues = "${commercial.sale.save.queue-name}")
    public void onCreate(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Venta recibida para crear, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }

    @RabbitListener(queues = "${commercial.sale.update.queue-name}")
    public void onUpdate(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Venta recibida para actualizar, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }

    @RabbitListener(queues = "${commercial.sale.deactivate.queue-name}")
    public void onDeactivate(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Venta recibida para desactivar, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }
}
