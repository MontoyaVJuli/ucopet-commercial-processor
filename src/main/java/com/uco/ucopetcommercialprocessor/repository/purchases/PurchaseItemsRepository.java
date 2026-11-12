package com.uco.ucopetcommercialprocessor.repository.purchases;

import com.uco.ucopetcommercialprocessor.domain.purchases.PurchaseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseItemsRepository extends JpaRepository<PurchaseItemEntity, UUID> {

    List<PurchaseItemEntity> findByPurchaseId(UUID purchaseId);
}
