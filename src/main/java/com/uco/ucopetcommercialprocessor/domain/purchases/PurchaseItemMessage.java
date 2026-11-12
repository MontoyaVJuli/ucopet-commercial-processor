package com.uco.ucopetcommercialprocessor.domain.purchases;

import java.math.BigDecimal;
import java.util.UUID;

// Forma de cada item dentro del mensaje JSON que publica ucopetapi
// (PurchaseItem serializado). No es una entidad JPA: solo se usa para
// leer el JSON; quien persiste de verdad es PurchaseItemEntity.
public class PurchaseItemMessage {

    private UUID id;
    private UUID productId;
    private String itemType;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String taxCategory;

    public PurchaseItemMessage() {
        // Constructor vacio requerido por Jackson para deserializar el
        // arreglo "items" del mensaje JSON por reflexion.
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public String getTaxCategory() { return taxCategory; }
    public void setTaxCategory(String taxCategory) { this.taxCategory = taxCategory; }
}
