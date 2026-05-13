package be.kdg.dishsg.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
    List<OrderJpaEntity> findByRestaurantId(UUID restaurantId);
    List<OrderJpaEntity> findByStatusAndCreatedAtBefore(String status, java.time.LocalDateTime cutoff);
    int countByRestaurantIdAndStatusIn(UUID restaurantId, List<String> statuses);
}
