package com.uco.ucopetcommercialprocessor.service.purchases;

import com.uco.ucopetcommercialprocessor.domain.purchases.PurchaseItemEntity;
import com.uco.ucopetcommercialprocessor.domain.purchases.PurchaseItemMessage;
import com.uco.ucopetcommercialprocessor.domain.purchases.PurchasesDomain;
import com.uco.ucopetcommercialprocessor.exception.purchases.PurchasesValidationException;
import com.uco.ucopetcommercialprocessor.repository.purchases.PurchaseItemsRepository;
import com.uco.ucopetcommercialprocessor.repository.purchases.PurchasesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;
    private final PurchaseItemsRepository purchaseItemsRepository;

    public PurchasesService(PurchasesRepository purchasesRepository,
                             PurchaseItemsRepository purchaseItemsRepository) {
        this.purchasesRepository = purchasesRepository;
        this.purchaseItemsRepository = purchaseItemsRepository;
    }

    @Transactional
    public void create(PurchasesDomain incoming) {
        validate(incoming);
        if (purchasesRepository.existsById(incoming.getId())) {
            return;
        }
        purchasesRepository.save(incoming);
        saveItems(incoming.getId(), incoming.getItems());
    }

    // Antes esto nunca se guardaba: el mensaje traia los items (para
    // calcular subtotal/impuestos/total en ucopetapi), pero esta clase solo
    // insertaba la fila de "purchases" y los items se perdian, por lo que
    // el detalle de la compra siempre mostraba "Sin items".
    private void saveItems(UUID purchaseId, List<PurchaseItemMessage> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        for (PurchaseItemMessage itemMessage : items) {
            PurchaseItemEntity item = new PurchaseItemEntity();
            item.setId(itemMessage.getId() != null ? itemMessage.getId() : UUID.randomUUID());
            item.setPurchaseId(purchaseId);
            item.setProductId(itemMessage.getProductId());
            item.setItemType(PurchaseItemEntity.ItemType.valueOf(itemMessage.getItemType()));
            item.setQuantity(itemMessage.getQuantity());
            item.setUnitPrice(itemMessage.getUnitPrice());
            item.setSubtotal(itemMessage.getSubtotal());
            item.setTaxCategory(PurchaseItemEntity.TaxCategory.valueOf(itemMessage.getTaxCategory()));
            purchaseItemsRepository.save(item);
        }
    }

    @Transactional
    public void update(PurchasesDomain incoming) {
        validate(incoming);
        PurchasesDomain purchase = purchasesRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Compra no encontrada: " + incoming.getId()));

        purchase.setPurchaseNumber(incoming.getPurchaseNumber());
        purchase.setSupplierId(incoming.getSupplierId());
        purchase.setHeadquarterId(incoming.getHeadquarterId());
        purchase.setPurchaseDate(incoming.getPurchaseDate());
        purchase.setSubtotal(incoming.getSubtotal());
        purchase.setTotalTaxes(incoming.getTotalTaxes());
        purchase.setTotal(incoming.getTotal());
        purchase.setStatus(incoming.getStatus());
        purchase.setExpenseId(incoming.getExpenseId());
        purchase.setHasDiscount(incoming.isHasDiscount());
        purchase.setUpdatedAt(incoming.getUpdatedAt());
        purchase.setUpdatedByPersonId(incoming.getUpdatedByPersonId());

        purchasesRepository.save(purchase);
    }

    @Transactional
    public void deactivate(PurchasesDomain incoming) {
        PurchasesDomain purchase = purchasesRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Compra no encontrada: " + incoming.getId()));

        purchase.setStatus(PurchasesDomain.Status.CANCELLED);

        purchasesRepository.save(purchase);
    }

    @Transactional
    public void save(PurchasesDomain incoming) {
        if (purchasesRepository.existsById(incoming.getId())) {
            update(incoming);
        } else {
            create(incoming);
        }
    }

    private void validate(PurchasesDomain purchase) {
        if (purchase.getId() == null) {
            throw new PurchasesValidationException("La compra debe tener un id");
        }
        if (purchase.getPurchaseNumber() == null || purchase.getPurchaseNumber().isBlank()) {
            throw new PurchasesValidationException("La compra debe tener un numero de compra");
        }
        if (purchase.getSupplierId() == null) {
            throw new PurchasesValidationException("La compra debe tener un proveedor");
        }
        if (purchase.getHeadquarterId() == null) {
            throw new PurchasesValidationException("La compra debe tener una sede");
        }
        if (purchase.getTotal() == null || purchase.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new PurchasesValidationException("El total de la compra no puede ser negativo");
        }
        if (purchase.getStatus() == null) {
            throw new PurchasesValidationException("La compra debe tener un estado");
        }
    }
}
