package com.uco.ucopetcommercialprocessor.repository.sale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.ItemSaleDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemSaleJpaRepository
        extends JpaRepository<ItemSaleDomain, UUID> {
}