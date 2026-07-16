package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event raised when a restaurant rejects a pending order, including the rejection reason.
public record OrderRejectedEvent(UUID orderId, String reason, LocalDateTime occurredAt) implements OrderEvent {}
