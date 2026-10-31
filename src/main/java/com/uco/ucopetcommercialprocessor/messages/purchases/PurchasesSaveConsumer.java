package com.uco.ucopetcommercialprocessor.messages.purchases;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uco.ucopetcommercialprocessor.domain.purchases.PurchasesDomain;
import com.uco.ucopetcommercialprocessor.exception.purchases.PurchasesValidationException;
import com.uco.ucopetcommercialprocessor.service.purchases.PurchasesService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ucopetapi manda la entidad Purchase completa (items, fechas, auditoria...),
 * no solo los campos que aqui nos interesan. Por eso no usamos el
 * MapperJsonObjeto compartido (que falla si hay propiedades desconocidas):
 * usamos un ObjectMapper propio que las ignora, igual que ya hace
 * HealthPlanSaveConsumer para el mismo caso.
 */
@Component
public class PurchasesSaveConsumer {

    private final PurchasesService purchasesService;
    private final ObjectMapper mapper;

    public PurchasesSaveConsumer(PurchasesService purchasesService) {
        this.purchasesService = purchasesService;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RabbitListener(queues = "${commercial.purchases.save.queue-name}")
    public void consume(String message) {
        try {
            PurchasesDomain purchase = mapper.readValue(message, PurchasesDomain.class);
            purchasesService.save(purchase);
        } catch (PurchasesValidationException ex) {
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException("Error al procesar la compra: " + ex.getMessage(), ex);
        }
    }
}
