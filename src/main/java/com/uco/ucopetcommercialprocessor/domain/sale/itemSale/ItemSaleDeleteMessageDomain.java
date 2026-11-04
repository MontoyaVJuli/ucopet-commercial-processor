package com.uco.ucopetcommercialprocessor.domain.sale.itemSale;

import java.util.UUID;

public record ItemSaleDeleteMessageDomain(
        UUID itemSaleId,
        UUID saleOrderId
) {
}