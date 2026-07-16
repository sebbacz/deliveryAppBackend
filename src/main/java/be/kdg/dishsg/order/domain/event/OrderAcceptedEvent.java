package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event raised when a restaurant accepts a pending order.
public record OrderAcceptedEvent(UUID orderId, LocalDateTime occurredAt) implements OrderEvent {}
