package be.sebastiangondek.kdg.orders.ports.out;

import be.sebastiangondek.kdg.orders.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Out-port for reading orders.
public interface LoadOrderPort {
    Optional<Order> findById(UUID id);
    List<Order> findByRestaurantId(UUID restaurantId);
    List<UUID> findPendingOrderIdsBefore(LocalDateTime cutoff);
    int countActiveByRestaurantId(UUID restaurantId);
}
