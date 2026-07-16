package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain event raised when the kitchen marks an accepted order ready for courier pickup.
public record OrderMarkedReadyForPickupEvent(UUID orderId, LocalDateTime occurredAt) implements OrderEvent {}
