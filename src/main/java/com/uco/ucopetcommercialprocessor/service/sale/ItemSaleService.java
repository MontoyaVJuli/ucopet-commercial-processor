package com.uco.ucopetcommercialprocessor.service.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.ItemSaleDomain;
import com.uco.ucopetcommercialprocessor.dto.sale.ItemSaleMessageDTO;
import com.uco.ucopetcommercialprocessor.dto.sale.ItemSaleDeleteMessageDTO;
import com.uco.ucopetcommercialprocessor.repository.sale.ItemSaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ItemSaleService {

    private static final Logger log =
            LoggerFactory.getLogger(ItemSaleService.class);

    private final ItemSaleRepository itemSaleRepository;

    public ItemSaleService(
            final ItemSaleRepository itemSaleRepository) {
        this.itemSaleRepository = itemSaleRepository;
    }

    @Transactional
    public void create(final ItemSaleMessageDTO message) {
        validate(message);

        if (itemSaleRepository.existsById(message.id())) {
            log.info("El ítem {} ya existe; se evita duplicarlo",
                    message.id());
            return;
        }

        final ItemSaleDomain item = toDomain(message);
        itemSaleRepository.save(item);

        log.info("Ítem {} guardado correctamente", message.id());
    }

    @Transactional
    public void update(final ItemSaleMessageDTO message) {
        validate(message);

        final ItemSaleDomain item =
                itemSaleRepository.findById(message.id())
                        .orElseThrow(() -> new IllegalStateException(
                                "No existe el ítem: " + message.id()));

        if (!item.getSaleOrderId().equals(message.saleOrderId())) {
            throw new IllegalArgumentException(
                    "El ítem no pertenece a la orden indicada");
        }

        item.setQuantity(message.quantity());
        item.setUnitPrice(message.unitPrice());
        item.setSubtotal(message.subtotal());

        itemSaleRepository.save(item);

        log.info("Ítem {} actualizado correctamente", message.id());
    }

    @Transactional
    public void delete(final ItemSaleDeleteMessageDTO message) {
        if (message == null
                || message.itemSaleId() == null
                || message.saleOrderId() == null) {
            throw new IllegalArgumentException(
                    "El mensaje de eliminación debe incluir los identificadores");
        }

        final UUID itemSaleId = message.itemSaleId();

        final ItemSaleDomain item =
                itemSaleRepository.findById(itemSaleId).orElse(null);

        if (item == null) {
            log.info("El ítem {} ya no existe", itemSaleId);
            return;
        }

        if (!item.getSaleOrderId().equals(message.saleOrderId())) {
            throw new IllegalArgumentException(
                    "El ítem no pertenece a la orden indicada");
        }

        itemSaleRepository.deleteById(itemSaleId);

        log.info("Ítem {} eliminado correctamente", itemSaleId);
    }

    private ItemSaleDomain toDomain(
            final ItemSaleMessageDTO message) {
        return new ItemSaleDomain(
                message.id(),
                message.saleOrderId(),
                message.itemId(),
                com.uco.ucopetcommercialprocessor.dto.sale.enums.ItemType
                        .valueOf(message.itemType()),
                com.uco.ucopetcommercialprocessor.domain.sale.enums.TaxCategory
                        .valueOf(message.taxCategory()),
                message.quantity(),
                message.unitPrice(),
                message.subtotal()
        );
    }

    private void validate(final ItemSaleMessageDTO message) {
        if (message == null
                || message.id() == null
                || message.saleOrderId() == null
                || message.itemId() == null) {
            throw new IllegalArgumentException(
                    "El mensaje del ítem debe incluir sus identificadores");
        }

        if (message.itemType() == null
                || message.taxCategory() == null
                || message.quantity() == null
                || message.quantity() <= 0
                || message.unitPrice() == null
                || message.subtotal() == null
                || message.unitPrice().signum() < 0
                || message.subtotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "El mensaje del ítem contiene datos inválidos");
        }
    }
}