package be.kdg.dishsg.order.ports.out;

import be.kdg.dishsg.order.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findByRestaurantId(UUID restaurantId);
    List<Order> findPendingOrdersBefore(LocalDateTime cutoff);
}
