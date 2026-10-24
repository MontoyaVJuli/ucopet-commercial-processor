
package com.uco.ucopetcommercialprocessor.repository.sale.impl;

import com.uco.ucopetcommercialprocessor.domain.sale.SaleOrderDomain;
import com.uco.ucopetcommercialprocessor.repository.sale.SaleOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SaleOrderRepositoryImpl implements SaleOrderRepository {

    private final SaleOrderJpaRepository jpaRepository;

    public SaleOrderRepositoryImpl(
            final SaleOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public SaleOrderDomain save(final SaleOrderDomain saleOrderDomain) {
        return jpaRepository.save(saleOrderDomain);
    }

    @Override
    public Optional<SaleOrderDomain> findById(final UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public boolean existsById(final UUID id) {
        return jpaRepository.existsById(id);
    }
}

