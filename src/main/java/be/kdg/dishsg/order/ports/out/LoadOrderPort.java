package be.kdg.dishsg.order.ports.out;

import be.kdg.dishsg.order.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadOrderPort {
    Optional<Order> findById(UUID id);
    List<Order> findByRestaurantId(UUID restaurantId);
    List<UUID> findPendingOrderIdsBefore(LocalDateTime cutoff);
    int countActiveByRestaurantId(UUID restaurantId);
}
