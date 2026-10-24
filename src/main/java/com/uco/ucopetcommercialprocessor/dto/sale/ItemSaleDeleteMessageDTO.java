package com.uco.ucopetcommercialprocessor.dto.sale;

import java.util.UUID;

public record ItemSaleDeleteMessageDTO(
        UUID itemSaleId,
        UUID saleOrderId
) {
}