package com.uco.ucopetcommercialprocessor.repository.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.ItemSaleDomain;

import java.util.Optional;
import java.util.UUID;

public interface ItemSaleRepository {

    ItemSaleDomain save(ItemSaleDomain itemSaleDomain);

    Optional<ItemSaleDomain> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
