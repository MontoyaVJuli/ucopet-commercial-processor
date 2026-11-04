package com.uco.ucopetcommercialprocessor.service.sale.sale;

import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleDeactivateMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.sale.SaleOrderMessageDomain;

import java.time.LocalDate;
import java.util.List;

public interface SaleOrderService {

    void processSaleCreation(SaleOrderMessageDomain message);

    void processSaleUpdate(SaleOrderMessageDomain message);

    void processSaleDeactivation(SaleDeactivateMessageDomain message);

    List<SaleOrderDomain> findSaleOrderByFilter(
            SaleOrderDomain filter,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    List<SaleOrderDomain> findAll();
}