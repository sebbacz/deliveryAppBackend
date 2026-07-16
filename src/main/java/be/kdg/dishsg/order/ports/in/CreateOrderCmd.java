package be.kdg.dishsg.order.ports.in;

import java.util.List;
import java.util.UUID;

public record CreateOrderCmd(
        UUID restaurantId,
        String customerName,
        String deliveryStreet,
        String deliveryNumber,
        String deliveryPostalCode,
        String deliveryCity,
        String deliveryCountry,
        String contactEmail,
        List<OrderItemCmd> items
) {
    public record OrderItemCmd(UUID dishId, String dishName, double price, int quantity) {}
}
