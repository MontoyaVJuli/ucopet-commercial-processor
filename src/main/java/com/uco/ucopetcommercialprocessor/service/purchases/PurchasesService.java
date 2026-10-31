package com.uco.ucopetcommercialprocessor.service.purchases;

import com.uco.ucopetcommercialprocessor.domain.purchases.PurchasesDomain;
import com.uco.ucopetcommercialprocessor.exception.purchases.PurchasesValidationException;
import com.uco.ucopetcommercialprocessor.repository.purchases.PurchasesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class PurchasesService {

    private final PurchasesRepository purchasesRepository;

    public PurchasesService(PurchasesRepository purchasesRepository) {
        this.purchasesRepository = purchasesRepository;
    }

    @Transactional
    public void create(PurchasesDomain incoming) {
        validate(incoming);
        if (purchasesRepository.existsById(incoming.getId())) {
            return;
        }
        purchasesRepository.save(incoming);
    }

    @Transactional
    public void update(PurchasesDomain incoming) {
        validate(incoming);
        PurchasesDomain purchase = purchasesRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Compra no encontrada: " + incoming.getId()));

        purchase.setPurchaseNumber(incoming.getPurchaseNumber());
        purchase.setSupplierId(incoming.getSupplierId());
        purchase.setHeadquarterId(incoming.getHeadquarterId());
        purchase.setTotal(incoming.getTotal());
        purchase.setStatus(incoming.getStatus());

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
