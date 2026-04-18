package com.order.inventory_service.consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.order.inventory_service.service.PriorityOrder;
import com.order.inventory_service.service.PriorityOrderQueue;
//import com.order.inventory_service.service.OrderProcessingQueue;
import com.retail.common.events.OrderCreatedEvent;

@Component
public class OrderCreatedEventConsumer {
private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedEventConsumer.class);
//private final OrderProcessingQueue orderProcessingQueue;
private final PriorityOrderQueue priorityBlockingQueue;
public OrderCreatedEventConsumer(PriorityOrderQueue priorityBlockingQueue) {
        //this.orderProcessingQueue = orderProcessingQueue;
        this.priorityBlockingQueue = priorityBlockingQueue;
    }
    @KafkaListener(topics = "order-created",groupId = "inventory-service-v2")
    public void consume(OrderCreatedEvent event) {
        int priority = calculatePriority(event);
        //log.info("Pushing order to processing queue: {}", event);
       // orderProcessingQueue.add(event);
        log.info("Order {} received with priority {}", event.orderId(), priority);
        priorityBlockingQueue.add(new PriorityOrder(event, priority));
    }

    private int calculatePriority(OrderCreatedEvent event) {

        if (event.quantity() > 5) {
            return 10; // VIP order
        }

        return 1; // normal order
    }
}
