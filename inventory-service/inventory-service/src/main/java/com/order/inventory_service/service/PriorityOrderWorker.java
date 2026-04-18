package com.order.inventory_service.service;

import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PriorityOrderWorker {
    private final ExecutorService workerPool;
    private final PriorityOrderQueue queue;
    private final InventoryService inventoryService;

    public PriorityOrderWorker(
            PriorityOrderQueue queue,
            InventoryService inventoryService,
            ExecutorService workerPool) {

        this.queue = queue;
        this.inventoryService = inventoryService;
        this.workerPool=workerPool;
    }

    @PostConstruct
    public void startWorker() {
        Thread worker = new Thread(() -> {
         System.out.println("PriorityOrderWorker started...");
//Single Thread Worker for processing orders based on priority
       // Thread worker = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    PriorityOrder order = queue.take();
                    System.out.println("Processing priority order: " + order.getEvent().orderId());
                    workerPool.submit(() ->inventoryService.processOrder(order.getEvent()));

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    System.out.println("Worker interrupted. Stopping worker.");
                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

       // });

       // worker.start();
    });
        worker.setDaemon(true);
        worker.start();
    }
}
