package com.retail.common.events;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        UUID orderId,
        UUID productId,
        int quantity,
        Instant occurredAt
) implements DomainEvent {}
