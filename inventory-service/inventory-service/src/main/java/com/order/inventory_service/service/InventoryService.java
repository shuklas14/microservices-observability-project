package com.order.inventory_service.service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.order.inventory_service.domain.Inventory;
import com.order.inventory_service.domain.ProcessedEvent;
import com.order.inventory_service.repository.InventoryRepository;
import com.order.inventory_service.repository.ProcessedEventRepository;
import com.retail.common.events.OrderCreatedEvent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class InventoryService{
    private final InventoryRepository inventoryRepository;
    private final ProcessedEventRepository processedEventRepository;
    public InventoryService(InventoryRepository inventoryRepository, ProcessedEventRepository processedEventRepository) {
        this.inventoryRepository = inventoryRepository;
        this.processedEventRepository = processedEventRepository;
    }

    @Cacheable(value = "inventory", key = "#event.productId")
    public void reserveInventory(OrderCreatedEvent event) {
        @SuppressWarnings("null")
        Inventory inventory = inventoryRepository.findById(event.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));  
        inventory.reserve(event.quantity());
        inventoryRepository.save(inventory);
        // if(true){
        // throw new RuntimeException("Simulated failure");
   // }
    }

    @SuppressWarnings("null")
    @CircuitBreaker(name="inventoryService",fallbackMethod = "circuitBreakerFallback")
    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @org.springframework.retry.annotation.Backoff(delay = 1000, multiplier = 2))
    public void processOrder(OrderCreatedEvent event) {
        if(processedEventRepository.existsById(event.eventId())){
            System.out.println("Order " + event.orderId() + " already processed. Skipping.");
            return;
        }
        Deque<String> operationStack = new ArrayDeque<>();
//try{
        CompletableFuture<Void> inventoryTask =
                CompletableFuture.runAsync(() ->{ 
                    reserveInventory(event);
                    synchronized (operationStack) {
                        operationStack.push("RESERVE_INVENTORY");
                    }
    });

        CompletableFuture<Void> notificationTask =
                CompletableFuture.runAsync(() -> { 
                    sendNotification(event);
                    synchronized (operationStack) {
                        operationStack.push("SEND_NOTIFICATION");
                    }
                });

        CompletableFuture<Void> auditTask =
                CompletableFuture.runAsync(() -> { 
                    auditLog(event);
                    synchronized (operationStack) {
                        operationStack.push("AUDIT_LOG");
                    }
                });

        CompletableFuture.allOf(
                inventoryTask,
                notificationTask,
                auditTask
        ).join();
        processedEventRepository.save(new ProcessedEvent(event.eventId()));
//    } 
//     catch(Exception e){
//         System.out.println("Failure occurred. Starting rollback.");

//             while (!operationStack.isEmpty()) {

//                 String operation = operationStack.pop();

//                 rollback(operation, event);
//             }
//     }
    }

    private void sendNotification(OrderCreatedEvent event) {

    System.out.println("Sending notification for order " + event.orderId());
}

private void auditLog(OrderCreatedEvent event) {

    System.out.println("Writing audit log for order " + event.orderId());
}

private void rollback(String operation, OrderCreatedEvent event) {

        switch (operation) {

            case "RESERVE_INVENTORY" ->
                    System.out.println("Rolling back inventory for " + event.productId());

            case "SEND_NOTIFICATION" ->
                    System.out.println("Rolling back notification for " + event.orderId());

            case "AUDIT_LOG" ->
                    System.out.println("Rolling back audit log for " + event.orderId());
        }
    }

//     @Recover
// public void recover(Exception e, OrderCreatedEvent event) {

//     System.out.println("All retries failed for order " + event.orderId());
//     //rollback(operation, event);

//     // Example actions
//     // send to dead letter queue
//     // mark order failed
// }

public void circuitBreakerFallback(OrderCreatedEvent event, Throwable t) {

    System.out.println("Circuit breaker opened for order " + event.orderId());
    // Example actions
    // send to dead letter queue
    // mark order failed
}
}