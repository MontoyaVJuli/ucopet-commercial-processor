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
@Table(name = "purchase_items")
public class PurchaseItemEntity {

    @Id
    private UUID id;

    @Column(name = "purchase_id", nullable = false)
    private UUID purchaseId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_category", nullable = false)
    private TaxCategory taxCategory;

    public enum ItemType { PRODUCT, SERVICE }

    public enum TaxCategory { STANDARD, REDUCED, EXEMPT }

    public PurchaseItemEntity() {
        // Constructor vacio requerido por JPA/Hibernate para instanciar la
        // entidad por reflexion al recuperarla de la base de datos.
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getPurchaseId() { return purchaseId; }
    public void setPurchaseId(UUID purchaseId) { this.purchaseId = purchaseId; }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public TaxCategory getTaxCategory() { return taxCategory; }
    public void setTaxCategory(TaxCategory taxCategory) { this.taxCategory = taxCategory; }
}
