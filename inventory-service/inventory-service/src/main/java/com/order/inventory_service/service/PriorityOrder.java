package com.order.inventory_service.service;


import com.retail.common.events.OrderCreatedEvent;

public class PriorityOrder implements Comparable<PriorityOrder> {

    private final OrderCreatedEvent event;
    private final int priority;

    public PriorityOrder(OrderCreatedEvent event, int priority) {
        this.event = event;
        this.priority = priority;
    }

    public OrderCreatedEvent getEvent() {
        return event;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PriorityOrder other) {
        return Integer.compare(other.priority, this.priority);
    }
}
