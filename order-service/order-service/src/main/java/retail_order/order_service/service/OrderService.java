package retail_order.order_service.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.retail.common.events.OrderCreatedEvent;

import retail_order.order_service.api.CreateOrderRequest;
import retail_order.order_service.api.CreateOrderResponse;
import retail_order.order_service.domain.Order;
import retail_order.order_service.events.OrderEventPublisher;
import retail_order.order_service.repository.OrderRepository;

@Service
public class OrderService {
 private final OrderRepository repository;
    private final OrderEventPublisher publisher;

    public OrderService(OrderRepository repository, OrderEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public CreateOrderResponse create(CreateOrderRequest request) {

        Order order = new Order(
                UUID.randomUUID(),
                request.productId(),
                request.quantity()
        );

        repository.save(order);

        publisher.publish(new OrderCreatedEvent(
                UUID.randomUUID(),
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                Instant.now()
        ));

        return new CreateOrderResponse(order.getId(), order.getStatus());
    }
}
