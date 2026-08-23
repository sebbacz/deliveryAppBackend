package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

//  order is delivered; polling on the tracking page stops after this.
public record OrderDeliveredEvent(
        UUID orderId,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public OrderDeliveredEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.occurredAt = occurredAt;
    }
}
