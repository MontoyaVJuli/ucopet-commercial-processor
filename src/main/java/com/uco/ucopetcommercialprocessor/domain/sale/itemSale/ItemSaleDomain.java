package com.uco.ucopetcommercialprocessor.domain.sale.itemSale;

import com.uco.ucopetcommercialprocessor.domain.sale.enums.TaxCategory;
import com.uco.ucopetcommercialprocessor.dto.sale.itemSale.ItemSaleDTO;
import com.uco.ucopetcommercialprocessor.dto.sale.enums.ItemType;
import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item_sale")
public class ItemSaleDomain implements Persistable<UUID> {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "sale_order_id", nullable = false)
    private UUID saleOrderId;

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 20)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_category", nullable = false, length = 20)
    private TaxCategory taxCategory;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Transient
    private boolean isNew = true;

    protected ItemSaleDomain() {

    }

    public ItemSaleDomain(UUID id, UUID saleOrderId, UUID itemId, ItemType itemType,
                          TaxCategory taxCategory, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
        this.id = id;
        this.saleOrderId = saleOrderId;
        this.itemId = itemId;
        this.itemType = itemType;
        this.taxCategory = taxCategory;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.isNew = true;
    }

    @Override
    public UUID getId() { return id; }
    public UUID getSaleOrderId() { return saleOrderId; }
    public UUID getItemId() { return itemId; }
    public ItemType getItemType() { return itemType; }
    public TaxCategory getTaxCategory() { return taxCategory; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markAsNotNew() {
        this.isNew = false;
    }

    public ItemSaleDTO toDTO() {
        return new ItemSaleDTO(id, saleOrderId, itemId, itemType, taxCategory, quantity, unitPrice, subtotal);
    }
}
