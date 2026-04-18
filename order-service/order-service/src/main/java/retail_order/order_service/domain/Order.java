package retail_order.order_service.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;

    private UUID productId;
    private int quantity;
    private String status;
    private Instant createdAt;

    protected Order() {} // JPA

    public Order(UUID id, UUID productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = "CREATED";
        this.createdAt = Instant.now();
    }

    // getters only (immutability at service level)
    public UUID getId() { return id; }
    public UUID getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
