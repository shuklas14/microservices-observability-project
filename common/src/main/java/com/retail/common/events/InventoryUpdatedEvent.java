package com.retail.common.events;

import java.time.Instant;
import java.util.UUID;

public record InventoryUpdatedEvent(
        UUID eventId,
        UUID productId,
        int availableQty,
        Instant occurredAt
) implements DomainEvent {}
