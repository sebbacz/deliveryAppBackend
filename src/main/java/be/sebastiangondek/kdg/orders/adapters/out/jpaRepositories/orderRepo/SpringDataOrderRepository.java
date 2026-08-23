package be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo;

import be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo.entities.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// jPA repository for the order projection table used for list and count queries only
public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
    List<OrderJpaEntity> findByRestaurantId(UUID restaurantId);
    List<OrderJpaEntity> findByStatusAndCreatedAtBefore(String status, java.time.LocalDateTime cutoff);
    int countByRestaurantIdAndStatusIn(UUID restaurantId, List<String> statuses);
}
