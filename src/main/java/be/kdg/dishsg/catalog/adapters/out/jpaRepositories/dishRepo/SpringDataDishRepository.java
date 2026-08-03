package be.kdg.dishsg.catalog.adapters.out.jpaRepositories.dishRepo;

import be.kdg.dishsg.catalog.adapters.out.jpaRepositories.dishRepo.entities.DishJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SpringDataDishRepository extends JpaRepository<DishJpaEntity, UUID> {

    List<DishJpaEntity> findByRestaurantId(UUID restaurantId);

    List<DishJpaEntity> findByRestaurantIdAndLiveNameIsNotNull(UUID restaurantId);

    long countByRestaurantIdAndLiveNameIsNotNull(UUID restaurantId);

    List<DishJpaEntity> findByRestaurantIdAndDraftNameIsNotNull(UUID restaurantId);

    List<DishJpaEntity> findByDraftNameIsNotNullAndScheduledAtIsNotNullAndScheduledAtLessThanEqual(LocalDateTime now);
}
