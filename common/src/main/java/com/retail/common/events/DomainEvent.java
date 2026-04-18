package com.retail.common.events;

import java.time.Instant;
import java.util.UUID;

public sealed interface DomainEvent
        permits OrderCreatedEvent, InventoryUpdatedEvent {

    UUID eventId();
    Instant occurredAt();
}
