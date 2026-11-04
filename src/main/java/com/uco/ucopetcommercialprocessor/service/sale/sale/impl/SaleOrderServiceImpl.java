package com.uco.ucopetcommercialprocessor.service.sale.sale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.enums.SaleOrderState;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleDeactivateMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderMessageDomain;
import com.uco.ucopetcommercialprocessor.exception.sale.SaleOrderNotFoundException;
import com.uco.ucopetcommercialprocessor.exception.sale.SaleOrderValidationException;
import com.uco.ucopetcommercialprocessor.repository.sale.sale.SaleOrderRepository;
import com.uco.ucopetcommercialprocessor.service.sale.itemSale.ItemSaleService;
import com.uco.ucopetcommercialprocessor.service.sale.sale.SaleOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderRepository saleOrderRepository;
    private final ItemSaleService itemSaleService;

    public SaleOrderServiceImpl(
            SaleOrderRepository saleOrderRepository,
            ItemSaleService itemSaleService) {

        this.saleOrderRepository = saleOrderRepository;
        this.itemSaleService = itemSaleService;
    }

    @Override
    @Transactional
    public void processSaleCreation(SaleOrderMessageDomain message) {

        validateMessage(message);
        persistSale(message);
    }

    @Override
    @Transactional
    public void processSaleUpdate(SaleOrderMessageDomain message) {

        validateMessage(message);
        persistSale(message);
    }

    private void persistSale(SaleOrderMessageDomain message) {

        SaleOrderDomain saleOrder = saleOrderRepository
                .findById(message.id())
                .orElseGet(() -> new SaleOrderDomain(
                        message.id(),
                        message.headquarterId(),
                        message.orderNumber(),
                        message.saleDate(),
                        message.clientID(),
                        message.petId(),
                        message.healthPlanId(),
                        message.subtotal(),
                        message.totalDiscount(),
                        message.totalTaxes(),
                        message.total(),
                        message.comment(),
                        message.state()
                ));

        saleOrder.setHeadquarterId(message.headquarterId());
        saleOrder.setOrderNumber(message.orderNumber());
        saleOrder.setSaleDate(message.saleDate());
        saleOrder.setClientID(message.clientID());
        saleOrder.setPetId(message.petId());
        saleOrder.setHealthPlanId(message.healthPlanId());
        saleOrder.setSubtotal(message.subtotal());
        saleOrder.setTotalDiscount(message.totalDiscount());
        saleOrder.setTotalTaxes(message.totalTaxes());
        saleOrder.setTotal(message.total());
        saleOrder.setComment(message.comment());
        saleOrder.setState(message.state());

        saleOrderRepository.save(saleOrder);
    }

    @Override
    @Transactional
    public void processSaleDeactivation(SaleDeactivateMessageDomain message) {

        if (message == null || message.saleOrderId() == null) {
            throw new SaleOrderValidationException(
                    "La información para desactivar la venta es obligatoria."
            );
        }

        SaleOrderDomain saleOrder = findExistingOrThrow(message.saleOrderId());

        if (saleOrder.getState() != SaleOrderState.ANULADA) {
            saleOrder.setState(SaleOrderState.ANULADA);
            saleOrderRepository.save(saleOrder);
        }
    }

    private void validateMessage(SaleOrderMessageDomain message) {

        if (message == null || message.id() == null) {
            throw new SaleOrderValidationException(
                    "La información de la venta recibida es obligatoria."
            );
        }
    }

    private SaleOrderDomain findExistingOrThrow(UUID id) {

        return saleOrderRepository.findById(id)
                .orElseThrow(() -> new SaleOrderNotFoundException(
                        "No existe una orden de venta con id " + id
                ));
    }

    private SaleOrderDomain populateItems(SaleOrderDomain saleOrder) {

        saleOrder.setItems(
                itemSaleService.listItemsBySaleOrder(saleOrder.getId())
        );

        return saleOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleOrderDomain> findSaleOrderByFilter(
            SaleOrderDomain filter,
            LocalDate dateFrom,
            LocalDate dateTo) {

        if (filter == null) {
            throw new SaleOrderValidationException(
                    "El filtro de búsqueda es obligatorio."
            );
        }

        if (dateFrom != null && dateTo != null
                && dateFrom.isAfter(dateTo)) {

            throw new SaleOrderValidationException(
                    "dateFrom no puede ser posterior a dateTo."
            );
        }

        return saleOrderRepository
                .findByFilter(filter, dateFrom, dateTo)
                .stream()
                .map(this::populateItems)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleOrderDomain> findAll() {

        return saleOrderRepository.findAll()
                .stream()
                .map(this::populateItems)
                .toList();
    }
}