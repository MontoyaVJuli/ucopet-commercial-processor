package com.uco.ucopetcommercialprocessor.service.sale.itemSale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.enums.TaxCategory;
import com.uco.ucopetcommercialprocessor.domain.sale.enums.ItemType;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDeleteMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleMessageDomain;
import com.uco.ucopetcommercialprocessor.exception.sale.SaleOrderNotFoundException;
import com.uco.ucopetcommercialprocessor.exception.sale.SaleOrderValidationException;
import com.uco.ucopetcommercialprocessor.repository.sale.itemSale.ItemSaleRepository;
import com.uco.ucopetcommercialprocessor.repository.sale.sale.SaleOrderRepository;
import com.uco.ucopetcommercialprocessor.service.sale.itemSale.ItemSaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ItemSaleServiceImpl implements ItemSaleService {

    private final ItemSaleRepository itemSaleRepository;
    private final SaleOrderRepository saleOrderRepository;

    public ItemSaleServiceImpl(
            ItemSaleRepository itemSaleRepository,
            SaleOrderRepository saleOrderRepository) {

        this.itemSaleRepository = itemSaleRepository;
        this.saleOrderRepository = saleOrderRepository;
    }

    @Override
    @Transactional
    public void processItemCreation(ItemSaleMessageDomain message) {

        validateMessage(message);
        validateSaleOrder(message.saleOrderId());

        ItemSaleDomain item = itemSaleRepository.findById(message.id())
                .orElseGet(() -> new ItemSaleDomain(
                        message.id(),
                        message.saleOrderId(),
                        message.itemId(),
                        ItemType.valueOf(message.itemType()),
                        TaxCategory.valueOf(message.taxCategory()),
                        message.quantity(),
                        message.unitPrice(),
                        message.subtotal()
                ));

        if (!item.getSaleOrderId().equals(message.saleOrderId())) {
            throw new SaleOrderValidationException(
                    "El ítem ya está asociado a otra orden de venta."
            );
        }

        updateItemData(item, message);

        itemSaleRepository.save(item);
    }

    @Override
    @Transactional
    public void processItemUpdate(ItemSaleMessageDomain message) {

        validateMessage(message);

        ItemSaleDomain item = itemSaleRepository.findById(message.id())
                .orElseThrow(() -> new SaleOrderValidationException(
                        "No existe un ítem de venta con id " + message.id()
                ));

        if (!item.getSaleOrderId().equals(message.saleOrderId())) {
            throw new SaleOrderValidationException(
                    "El ítem no pertenece a la orden de venta indicada."
            );
        }

        updateItemData(item, message);

        itemSaleRepository.save(item);
    }

    @Override
    @Transactional
    public void processItemDeletion(ItemSaleDeleteMessageDomain message) {

        if (message == null
                || message.itemSaleId() == null
                || message.saleOrderId() == null) {

            throw new SaleOrderValidationException(
                    "La información para eliminar el ítem de venta es obligatoria."
            );
        }

        ItemSaleDomain item = itemSaleRepository.findById(message.itemSaleId())
                .orElseThrow(() -> new SaleOrderValidationException(
                        "No existe un ítem de venta con id " + message.itemSaleId()
                ));

        if (!item.getSaleOrderId().equals(message.saleOrderId())) {
            throw new SaleOrderValidationException(
                    "El ítem no pertenece a la orden de venta indicada."
            );
        }

        itemSaleRepository.deleteById(message.itemSaleId());
    }

    private void updateItemData(
            ItemSaleDomain item,
            ItemSaleMessageDomain message) {

        item.setQuantity(message.quantity());
        item.setUnitPrice(message.unitPrice());
        item.setSubtotal(message.subtotal());
    }

    private void validateMessage(ItemSaleMessageDomain message) {

        if (message == null
                || message.id() == null
                || message.saleOrderId() == null
                || message.itemId() == null
                || message.itemType() == null
                || message.taxCategory() == null
                || message.quantity() == null
                || message.unitPrice() == null
                || message.subtotal() == null) {

            throw new SaleOrderValidationException(
                    "La información del ítem de venta está incompleta."
            );
        }

        if (message.quantity() <= 0
                || message.unitPrice().signum() < 0
                || message.subtotal().signum() < 0) {

            throw new SaleOrderValidationException(
                    "Los valores del ítem de venta no son válidos."
            );
        }
    }

    private void validateSaleOrder(UUID saleOrderId) {

        if (!saleOrderRepository.existsById(saleOrderId)) {
            throw new SaleOrderNotFoundException(
                    "No existe una orden de venta con id " + saleOrderId
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemSaleDomain> listItemsBySaleOrder(UUID saleOrderId) {

        validateSaleOrder(saleOrderId);

        return itemSaleRepository.findBySaleOrderId(saleOrderId);
    }
}