package com.uco.ucopetcommercialprocessor.domain.purchases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "purchases")
public class PurchasesDomain {

    @Id
    private UUID id;

    @Column(name = "purchase_number")
    private String purchaseNumber;

    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "headquarter_id")
    private UUID headquarterId;

    @Column(name = "total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        PENDING, RECEIVED, CANCELLED, LINKED
    }

    public PurchasesDomain() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getPurchaseNumber() { return purchaseNumber; }
    public void setPurchaseNumber(String purchaseNumber) { this.purchaseNumber = purchaseNumber; }

    public UUID getSupplierId() { return supplierId; }
    public void setSupplierId(UUID supplierId) { this.supplierId = supplierId; }

    public UUID getHeadquarterId() { return headquarterId; }
    public void setHeadquarterId(UUID headquarterId) { this.headquarterId = headquarterId; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
