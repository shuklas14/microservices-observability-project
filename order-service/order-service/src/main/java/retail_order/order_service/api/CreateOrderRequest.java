package retail_order.order_service.api;
import java.util.UUID;

public record CreateOrderRequest(
        UUID productId,
        int quantity
) {}
