package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

// mandatory rejection reason, which is transferd to customer on the tracking page.
public record OrderRejectedEvent(
        UUID orderId,
        String reason,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public OrderRejectedEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("reason") String reason,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.reason = reason;
        this.occurredAt = occurredAt;
    }
}
