package com.order.inventory_service.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    private UUID productId;

    private int availableQty;
    private int reservedQty;

    protected Inventory() {}

    public Inventory(UUID productId, int availableQty) {
        this.productId = productId;
        this.availableQty = availableQty;
        this.reservedQty = 0;
    }

    public void reserve(int qty) {
        if (availableQty < qty) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.availableQty -= qty;
        this.reservedQty += qty;
    }

    public UUID getProductId() { return productId; }
    public int getAvailableQty() { return availableQty; }
    public int getReservedQty() { return reservedQty; }
}