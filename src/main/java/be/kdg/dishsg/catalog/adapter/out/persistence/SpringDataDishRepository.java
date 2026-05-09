package be.kdg.dishsg.catalog.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataDishRepository extends JpaRepository<DishJpaEntity, UUID> {
    List<DishJpaEntity> findByRestaurantId(UUID restaurantId);
    List<DishJpaEntity> findByRestaurantIdAndState(UUID restaurantId, String state);
    long countByRestaurantIdAndState(UUID restaurantId, String state);
}
