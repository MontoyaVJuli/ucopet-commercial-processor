package com.uco.ucopetcommercialprocessor.messages.sale.itemSale;

import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDeleteMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleMessageDomain;
import com.uco.ucopetcommercialprocessor.service.sale.itemSale.ItemSaleService;
import com.uco.ucopetcommercialprocessor.util.gson.MapperJsonObjeto;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ItemSaleMessageListener {

    private final ItemSaleService itemSaleService;
    private final MapperJsonObjeto mapperJsonObjeto;

    public ItemSaleMessageListener(
            ItemSaleService itemSaleService,
            MapperJsonObjeto mapperJsonObjeto) {
        this.itemSaleService = itemSaleService;
        this.mapperJsonObjeto = mapperJsonObjeto;
    }

    @RabbitListener(queues = "${commercial.sale-item.save.queue-name}")
    public void onCreate(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        ItemSaleMessageDomain message = mapperJsonObjeto
                .ejecutar(body, ItemSaleMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de creación del ítem de venta."
                ));

        itemSaleService.processItemCreation(message);
    }

    @RabbitListener(queues = "${commercial.sale-item.update.queue-name}")
    public void onUpdate(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        ItemSaleMessageDomain message = mapperJsonObjeto
                .ejecutar(body, ItemSaleMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de actualización del ítem de venta."
                ));

        itemSaleService.processItemUpdate(message);
    }

    @RabbitListener(queues = "${commercial.sale-item.delete.queue-name}")
    public void onDelete(
            String body,
            @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId) {

        ItemSaleDeleteMessageDomain message = mapperJsonObjeto
                .ejecutar(body, ItemSaleDeleteMessageDomain.class)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No fue posible convertir el mensaje de eliminación del ítem de venta."
                ));

        itemSaleService.processItemDeletion(message);
    }
}
