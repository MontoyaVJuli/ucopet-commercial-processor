package com.uco.ucopetcommercialprocessor.repository.purchases;

import com.uco.ucopetcommercialprocessor.domain.purchases.PurchasesDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchasesRepository extends JpaRepository<PurchasesDomain, UUID> {
}
