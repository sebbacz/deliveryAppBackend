package be.sebastiangondek.kdg.orders.domain.events;

import be.sebastiangondek.kdg.orders.domain.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// complete order state at the moment the customer submitted it.
public record OrderPlacedEvent(
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
        @JsonProperty("occurredAt") LocalDateTime occurredAt
) implements OrderEvent {}
