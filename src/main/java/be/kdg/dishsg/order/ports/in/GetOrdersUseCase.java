package be.kdg.dishsg.order.ports.in;

import be.kdg.dishsg.order.domain.Order;

import java.util.List;
import java.util.UUID;

public interface GetOrdersUseCase {
    List<Order> getOrdersForRestaurant(UUID restaurantId);
}
