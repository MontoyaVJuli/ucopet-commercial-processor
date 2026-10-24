package com.uco.ucopetcommercialprocessor.repository.sale.sale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleOrderJpaRepository
        extends JpaRepository<SaleOrderDomain, UUID> {
}
