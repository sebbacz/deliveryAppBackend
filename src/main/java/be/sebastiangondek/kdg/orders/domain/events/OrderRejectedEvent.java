package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

// mandatory rejection reason
public record OrderRejectedEvent(
        @JsonProperty("orderId") UUID orderId,
        @JsonProperty("reason") String reason,
        @JsonProperty("occurredAt") LocalDateTime occurredAt
) implements OrderEvent {}
