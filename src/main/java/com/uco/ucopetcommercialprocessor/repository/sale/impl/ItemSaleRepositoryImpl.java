package com.uco.ucopetcommercialprocessor.repository.sale.impl;


import com.uco.ucopetcommercialprocessor.domain.sale.ItemSaleDomain;
import com.uco.ucopetcommercialprocessor.repository.sale.ItemSaleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ItemSaleRepositoryImpl implements ItemSaleRepository {

    private final ItemSaleJpaRepository jpaRepository;

    public ItemSaleRepositoryImpl(
            final ItemSaleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ItemSaleDomain save(final ItemSaleDomain itemSaleDomain) {
        return jpaRepository.save(itemSaleDomain);
    }

    @Override
    public Optional<ItemSaleDomain> findById(final UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public boolean existsById(final UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(final UUID id) {
        jpaRepository.deleteById(id);
    }
}

