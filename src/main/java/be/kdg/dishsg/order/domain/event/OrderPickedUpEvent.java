package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event raised when the courier picks up the order from the restaurant.
public record OrderPickedUpEvent(UUID orderId, LocalDateTime occurredAt) implements OrderEvent {}
