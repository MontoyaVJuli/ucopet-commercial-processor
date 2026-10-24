package com.uco.ucopetcommercialprocessor.dto.sale.itemSale;

import java.util.UUID;

public record ItemSaleDeleteMessageDTO(
        UUID itemSaleId,
        UUID saleOrderId
) {
}