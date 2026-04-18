package retail_order.order_service.api;

import java.util.UUID;

public record CreateOrderResponse(
        UUID orderId,
        String status
) {}
