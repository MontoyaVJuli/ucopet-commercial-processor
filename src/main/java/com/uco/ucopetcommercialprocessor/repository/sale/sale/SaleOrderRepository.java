package com.uco.ucopetcommercialprocessor.repository.sale.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderDomain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleOrderRepository {

    SaleOrderDomain save(SaleOrderDomain saleOrderDomain);

    Optional<SaleOrderDomain> findById(UUID id);

    boolean existsById(UUID id);

    List<SaleOrderDomain> findAll();

    List<SaleOrderDomain> findByFilter(SaleOrderDomain filter, LocalDate dateFrom, LocalDate dateTo
    );

    Optional<SaleOrderDomain> findLastOrderNumberByHeadquarter(UUID headquarterId);

}
