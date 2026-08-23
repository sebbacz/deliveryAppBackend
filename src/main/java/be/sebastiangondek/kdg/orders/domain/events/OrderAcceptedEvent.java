package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

//   order to ACCEPTED; triggers restaurant to prepare the order.
public record OrderAcceptedEvent(
        UUID orderId,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public OrderAcceptedEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.occurredAt = occurredAt;
    }
}
