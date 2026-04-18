package com.order.inventory_service.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.retail.common.events.OrderCreatedEvent;

@Component
public class OrderProcessingQueue {
    BlockingQueue<OrderCreatedEvent> queue= new LinkedBlockingQueue<>();

    public void add(OrderCreatedEvent event) {
        queue.add(event);
    }

    public OrderCreatedEvent take() throws InterruptedException {
        return queue.take();
    }
}
