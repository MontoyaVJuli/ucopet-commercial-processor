package com.uco.ucopetcommercialprocessor.messages.sale.itemSale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ItemSaleMessageListener {

    private static final Logger log =
            LoggerFactory.getLogger(ItemSaleMessageListener.class);

    @RabbitListener(queues = "${commercial.sale-item.save.queue-name}")
    public void onCreate(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Ítem de venta recibido para crear, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }

    @RabbitListener(queues = "${commercial.sale-item.update.queue-name}")
    public void onUpdate(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Ítem de venta recibido para actualizar, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }

    @RabbitListener(queues = "${commercial.sale-item.delete.queue-name}")
    public void onDelete(
            final String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false)
            final String messageId
    ) {
        log.info("Ítem de venta recibido para eliminar, messageId={}", messageId);
        log.debug("Contenido recibido: {}", body);
    }
}
