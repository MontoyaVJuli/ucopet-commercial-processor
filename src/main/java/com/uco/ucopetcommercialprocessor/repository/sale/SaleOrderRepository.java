package com.uco.ucopetcommercialprocessor.repository.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.SaleOrderDomain;

import java.util.Optional;
import java.util.UUID;

public interface SaleOrderRepository {

    SaleOrderDomain save(SaleOrderDomain saleOrderDomain);

    Optional<SaleOrderDomain> findById(UUID id);

    boolean existsById(UUID id);
}

