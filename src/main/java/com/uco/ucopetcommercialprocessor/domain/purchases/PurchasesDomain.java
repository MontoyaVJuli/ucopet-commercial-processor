package com.uco.ucopetcommercialprocessor.domain.purchases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "total_taxes")
    private BigDecimal totalTaxes;

    @Column(name = "total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "expense_id")
    private UUID expenseId;

    @Column(name = "has_discount")
    private boolean hasDiscount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by_person_id")
    private UUID createdByPersonId;

    @Column(name = "updated_by_person_id")
    private UUID updatedByPersonId;

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

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotalTaxes() { return totalTaxes; }
    public void setTotalTaxes(BigDecimal totalTaxes) { this.totalTaxes = totalTaxes; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public UUID getExpenseId() { return expenseId; }
    public void setExpenseId(UUID expenseId) { this.expenseId = expenseId; }

    public boolean isHasDiscount() { return hasDiscount; }
    public void setHasDiscount(boolean hasDiscount) { this.hasDiscount = hasDiscount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public UUID getCreatedByPersonId() { return createdByPersonId; }
    public void setCreatedByPersonId(UUID createdByPersonId) { this.createdByPersonId = createdByPersonId; }

    public UUID getUpdatedByPersonId() { return updatedByPersonId; }
    public void setUpdatedByPersonId(UUID updatedByPersonId) { this.updatedByPersonId = updatedByPersonId; }
}
