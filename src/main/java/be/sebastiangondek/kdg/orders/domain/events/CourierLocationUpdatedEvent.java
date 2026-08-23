package be.sebastiangondek.kdg.orders.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

//  updates the live map marker on the customer tracking page.
public record CourierLocationUpdatedEvent(
        UUID orderId,
        double latitude,
        double longitude,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public CourierLocationUpdatedEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("latitude") double latitude,
            @JsonProperty("longitude") double longitude,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.occurredAt = occurredAt;
    }
}
