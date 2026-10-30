package com.uco.ucopetcommercialprocessor.service.sale.itemSale;

import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleDeleteMessageDomain;
import com.uco.ucopetcommercialprocessor.domain.sale.itemSale.ItemSaleMessageDomain;

import java.util.List;
import java.util.UUID;

public interface ItemSaleService {

    void processItemCreation(ItemSaleMessageDomain message);

    void processItemUpdate(ItemSaleMessageDomain message);

    void processItemDeletion(ItemSaleDeleteMessageDomain message);

    List<ItemSaleDomain> listItemsBySaleOrder(UUID saleOrderId);
}