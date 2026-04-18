package com.order.inventory_service.service;

import org.springframework.stereotype.Component;

import com.retail.common.events.OrderCreatedEvent;

import jakarta.annotation.PostConstruct;

@Component
public class OrderProcessingWorker {
    private final OrderProcessingQueue orderProcessingQueue;
    private final InventoryService inventoryService;
    public OrderProcessingWorker(OrderProcessingQueue orderProcessingQueue, InventoryService inventoryService) {
        this.orderProcessingQueue = orderProcessingQueue;
        this.inventoryService = inventoryService;
    }

    @PostConstruct
    public void startWorker() {
        Thread workerThread = new Thread(() -> {
            while (true) {
                try {
                    OrderCreatedEvent event = orderProcessingQueue.take();
                    inventoryService.processOrder(event);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });
        workerThread.start();
    }
}
