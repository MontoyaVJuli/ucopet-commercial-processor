package com.uco.ucopetcommercialprocessor.messages.sale.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleDeactivateMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderMessageDomain;
import com.uco.ucopetcommercialprocessor.service.sale.sale.SaleOrderService;
import com.uco.ucopetcommercialprocessor.util.gson.MapperJsonObjeto;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class SaleMessageListener {

    private final SaleOrderService saleOrderService;
    private final MapperJsonObjeto mapperJsonObjeto;

    public SaleMessageListener(
            SaleOrderService saleOrderService,
            MapperJsonObjeto mapperJsonObjeto) {
        this.saleOrderService = saleOrderService;
        this.mapperJsonObjeto = mapperJsonObjeto;
    }

    @RabbitListener(queues = "${commercial.sale.save.queue-name}")
    public void onCreate(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        SaleOrderMessageDomain message = mapperJsonObjeto
                .ejecutar(body, SaleOrderMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de creación de la orden de venta."
                ));

        saleOrderService.processSaleCreation(message);
    }

    @RabbitListener(queues = "${commercial.sale.update.queue-name}")
    public void onUpdate(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        SaleOrderMessageDomain message = mapperJsonObjeto
                .ejecutar(body, SaleOrderMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de actualización de la orden de venta."
                ));

        saleOrderService.processSaleUpdate(message);
    }

    @RabbitListener(queues = "${commercial.sale.deactivate.queue-name}")
    public void onDeactivate(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        SaleDeactivateMessageDomain message = mapperJsonObjeto
                .ejecutar(body, SaleDeactivateMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de desactivación de la orden de venta."
                ));

        saleOrderService.processSaleDeactivation(message);
    }
}
