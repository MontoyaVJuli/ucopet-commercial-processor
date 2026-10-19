package com.uco.ucopetcommercialprocessor.messages.provider;

import com.uco.ucopetcommercialprocessor.domain.provider.ProviderDomain;
import com.uco.ucopetcommercialprocessor.exception.provider.ProviderValidationException;
import com.uco.ucopetcommercialprocessor.service.provider.ProviderService;
import com.uco.ucopetcommercialprocessor.util.gson.MapperJsonObjeto;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
public class ProviderSaveConsumer {
    private final ProviderService providerService;
    private final MapperJsonObjeto mapper;
    public ProviderSaveConsumer(ProviderService providerService, MapperJsonObjeto mapper) {
        this.providerService = providerService;
        this.mapper = mapper;
    }
    @RabbitListener(queues = "${commercial.provider.save.queue-name}")
    public void consume(String message) {
        ProviderDomain provider = mapper.ejecutar(message, ProviderDomain.class)
                .orElseThrow(() -> new AmqpRejectAndDontRequeueException(
                        "El mensaje de guardar proveedor no es válido"));
        try {
            providerService.save(provider);
        } catch (ProviderValidationException ex) {
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
}
