package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event carrying the courier's latest GPS coordinates for an in-progress order.
public record OrderCourierLocationUpdatedEvent(
        UUID orderId,
        double latitude,
        double longitude,
        LocalDateTime occurredAt
) implements OrderEvent {}
