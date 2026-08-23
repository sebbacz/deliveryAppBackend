package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

//  delivery service confirms it has picked up the order from the restaurant.
public record OrderPickedUpEvent(
        UUID orderId,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public OrderPickedUpEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.occurredAt = occurredAt;
    }
}
