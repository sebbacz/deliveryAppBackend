package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event raised when the courier delivers the order to the customer.
public record OrderDeliveredEvent(UUID orderId, LocalDateTime occurredAt) implements OrderEvent {}
