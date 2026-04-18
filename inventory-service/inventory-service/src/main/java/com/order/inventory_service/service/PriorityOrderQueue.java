package com.order.inventory_service.service;

import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

@Component
public class PriorityOrderQueue {
    private final PriorityBlockingQueue<PriorityOrder> queue =
            new PriorityBlockingQueue<>();

    public void add(PriorityOrder order) {
        queue.add(order);
    }

    public PriorityOrder take() throws InterruptedException {
        return queue.take();
    }
}
