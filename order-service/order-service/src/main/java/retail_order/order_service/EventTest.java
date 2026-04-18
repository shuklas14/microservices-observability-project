package retail_order.order_service;

import java.time.Instant;
import java.util.UUID;

import com.retail.common.events.OrderCreatedEvent;

public class EventTest {
OrderCreatedEvent event = new OrderCreatedEvent(
        UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            2,
            Instant.now() 
);
}
