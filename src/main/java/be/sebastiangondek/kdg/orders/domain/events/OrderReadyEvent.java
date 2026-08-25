package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

// tells delivery service that the order is ready for courier pickup.
public record OrderReadyEvent(
        @JsonProperty("orderId") UUID orderId,
        @JsonProperty("occurredAt") LocalDateTime occurredAt
) implements OrderEvent {}
