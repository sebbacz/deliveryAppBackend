package be.kdg.dishsg.catalog.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {

    List<DishJpaEntity> findByRestaurantId(UUID restaurantId);

    long countByRestaurantIdAndStateAndInStockTrue(UUID restaurantId, String state);
}
