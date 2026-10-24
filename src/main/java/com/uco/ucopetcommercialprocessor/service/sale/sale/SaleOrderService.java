package com.uco.ucopetcommercialprocessor.service.sale.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderDomain;
import com.uco.ucopetcommercialprocessor.dto.sale.sale.SaleOrderMessageDTO;
import com.uco.ucopetcommercialprocessor.dto.sale.sale.SaleDeactivateMessageDTO;
import com.uco.ucopetcommercialprocessor.dto.sale.enums.SaleOrderState;
import com.uco.ucopetcommercialprocessor.repository.sale.sale.SaleOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SaleOrderService {

    private static final Logger log =
            LoggerFactory.getLogger(SaleOrderService.class);

    private final SaleOrderRepository saleOrderRepository;

    public SaleOrderService(
            final SaleOrderRepository saleOrderRepository) {
        this.saleOrderRepository = saleOrderRepository;
    }

    @Transactional
    public void create(final SaleOrderMessageDTO message) {
        validate(message);

        if (saleOrderRepository.existsById(message.id())) {
            log.info("La orden {} ya existe; se evita duplicarla",
                    message.id());
            return;
        }

        final SaleOrderDomain saleOrder = toDomain(message);
        saleOrderRepository.save(saleOrder);

        log.info("Orden {} guardada correctamente", message.id());
    }

    @Transactional
    public void update(final SaleOrderMessageDTO message) {
        validate(message);

        final SaleOrderDomain saleOrder =
                saleOrderRepository.findById(message.id())
                        .orElseThrow(() -> new IllegalStateException(
                                "No existe la orden: " + message.id()));

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

        log.info("Orden {} actualizada correctamente", message.id());
    }

    @Transactional
    public void deactivate(final SaleDeactivateMessageDTO message) {
        if (message == null || message.saleOrderId() == null) {
            throw new IllegalArgumentException(
                    "El mensaje de anulación debe incluir saleOrderId");
        }

        final UUID saleOrderId = message.saleOrderId();

        final SaleOrderDomain saleOrder =
                saleOrderRepository.findById(saleOrderId)
                        .orElseThrow(() -> new IllegalStateException(
                                "No existe la orden: " + saleOrderId));

        if (saleOrder.getState() == SaleOrderState.ANULADA) {
            log.info("La orden {} ya está anulada", saleOrderId);
            return;
        }

        saleOrder.setState(SaleOrderState.ANULADA);
        saleOrderRepository.save(saleOrder);

        log.info("Orden {} anulada correctamente", saleOrderId);
    }

    private SaleOrderDomain toDomain(
            final SaleOrderMessageDTO message) {
        return new SaleOrderDomain(
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
        );
    }

    private void validate(final SaleOrderMessageDTO message) {
        if (message == null || message.id() == null) {
            throw new IllegalArgumentException(
                    "El mensaje debe incluir el identificador de la orden");
        }

        if (message.headquarterId() == null
                || message.orderNumber() == null
                || message.orderNumber().isBlank()
                || message.saleDate() == null
                || message.clientID() == null
                || message.petId() == null
                || message.subtotal() == null
                || message.totalDiscount() == null
                || message.totalTaxes() == null
                || message.total() == null
                || message.state() == null) {
            throw new IllegalArgumentException(
                    "El mensaje de la orden contiene campos obligatorios vacíos");
        }
    }
}