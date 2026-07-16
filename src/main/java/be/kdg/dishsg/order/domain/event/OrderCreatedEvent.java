package be.kdg.dishsg.order.domain.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Domain event raised when a new customer order is placed.
public record OrderCreatedEvent(
        UUID orderId,
        UUID restaurantId,
        String customerName,
        String deliveryStreet,
        String deliveryNumber,
        String deliveryPostalCode,
        String deliveryCity,
        String deliveryCountry,
        String contactEmail,
        List<ItemData> items,
        LocalDateTime createdAt,
        LocalDateTime occurredAt
) implements OrderEvent {

    public record ItemData(UUID id, UUID dishId, String dishName, double price, int quantity) {}
}
