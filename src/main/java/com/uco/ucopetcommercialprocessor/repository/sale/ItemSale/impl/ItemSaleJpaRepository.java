package com.uco.ucopetcommercialprocessor.repository.sale.ItemSale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemSaleJpaRepository
        extends JpaRepository<ItemSaleDomain, UUID> {
}