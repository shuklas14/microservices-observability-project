package retail_order.order_service.events;

import com.retail.common.events.OrderCreatedEvent;

public interface OrderEventPublisher {
    void publish(OrderCreatedEvent event);
}
