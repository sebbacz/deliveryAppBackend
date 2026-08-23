package be.sebastiangondek.kdg.orders.domain.events;

import be.sebastiangondek.kdg.orders.domain.OrderItem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// complete  order state
public record OrderPlacedEvent(
        UUID orderId,
        UUID restaurantId,
        String customerName,
        String deliveryStreet,
        String deliveryNumber,
        String deliveryPostalCode,
        String deliveryCity,
        String deliveryCountry,
        String contactEmail,
        List<OrderItem> items,
        LocalDateTime occurredAt
) implements OrderEvent {

    @JsonCreator
    public OrderPlacedEvent(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("restaurantId") UUID restaurantId,
            @JsonProperty("customerName") String customerName,
            @JsonProperty("deliveryStreet") String deliveryStreet,
            @JsonProperty("deliveryNumber") String deliveryNumber,
            @JsonProperty("deliveryPostalCode") String deliveryPostalCode,
            @JsonProperty("deliveryCity") String deliveryCity,
            @JsonProperty("deliveryCountry") String deliveryCountry,
            @JsonProperty("contactEmail") String contactEmail,
            @JsonProperty("items") List<OrderItem> items,
            @JsonProperty("occurredAt") LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryNumber = deliveryNumber;
        this.deliveryPostalCode = deliveryPostalCode;
        this.deliveryCity = deliveryCity;
        this.deliveryCountry = deliveryCountry;
        this.contactEmail = contactEmail;
        this.items = items;
        this.occurredAt = occurredAt;
    }
}
