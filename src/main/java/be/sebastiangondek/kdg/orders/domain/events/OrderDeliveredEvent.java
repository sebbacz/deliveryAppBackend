package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

// order is delivered; polling on the tracking page stops after this.
public record OrderDeliveredEvent(
        @JsonProperty("orderId") UUID orderId,
        @JsonProperty("occurredAt") LocalDateTime occurredAt
) implements OrderEvent {}
