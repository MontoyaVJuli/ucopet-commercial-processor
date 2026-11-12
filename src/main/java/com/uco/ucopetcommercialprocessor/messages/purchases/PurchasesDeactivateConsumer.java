package com.uco.ucopetcommercialprocessor.messages.purchases;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.uco.ucopetcommercialprocessor.domain.purchases.PurchasesDomain;
import com.uco.ucopetcommercialprocessor.exception.purchases.PurchasesValidationException;
import com.uco.ucopetcommercialprocessor.service.purchases.PurchasesService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class PurchasesDeactivateConsumer {

    private final PurchasesService purchasesService;
    private final ObjectMapper mapper;

    public PurchasesDeactivateConsumer(PurchasesService purchasesService) {
        this.purchasesService = purchasesService;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule localDateTimeModule = new SimpleModule();
        localDateTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                return LocalDateTime.parse(parser.getText());
            }
        });
        this.mapper.registerModule(localDateTimeModule);
    }

    @RabbitListener(queues = "${commercial.purchases.deactivate.queue-name}")
    public void consume(String message) {
        try {
            PurchasesDomain purchase = mapper.readValue(message, PurchasesDomain.class);
            purchasesService.deactivate(purchase);
        } catch (PurchasesValidationException ex) {
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException("Error al procesar la desactivacion de la compra: " + ex.getMessage(), ex);
        }
    }
}
