package be.sebastiangondek.kdg.orders.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//   snapshot of an Order's state at a given sequence number.
public record OrderSnapshot(
        @JsonProperty("id") UUID id,
        @JsonProperty("restaurantId") UUID restaurantId,
        @JsonProperty("customerName") String customerName,
        @JsonProperty("deliveryStreet") String deliveryStreet,
        @JsonProperty("deliveryNumber") String deliveryNumber,
        @JsonProperty("deliveryPostalCode") String deliveryPostalCode,
        @JsonProperty("deliveryCity") String deliveryCity,
        @JsonProperty("deliveryCountry") String deliveryCountry,
        @JsonProperty("contactEmail") String contactEmail,
        @JsonProperty("items") List<OrderItem> items,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("status") OrderStatus status,
        @JsonProperty("rejectionReason") String rejectionReason,
        @JsonProperty("courierLatitude") Double courierLatitude,
        @JsonProperty("courierLongitude") Double courierLongitude,
        @JsonProperty("sequenceNumber") long sequenceNumber
) {
    public static OrderSnapshot of(Order order) {
        return new OrderSnapshot(
                order.getId(), order.getRestaurantId(), order.getCustomerName(),
                order.getDeliveryStreet(), order.getDeliveryNumber(), order.getDeliveryPostalCode(),
                order.getDeliveryCity(), order.getDeliveryCountry(), order.getContactEmail(),
                order.getItems(), order.getCreatedAt(), order.getStatus(),
                order.getRejectionReason(), order.getCourierLatitude(), order.getCourierLongitude(),
                order.getSequenceNumber()
        );
    }
}
