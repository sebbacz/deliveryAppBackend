package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.dishRepo;

import be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.dishRepo.entities.DishJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// PA repository for dishes.
public interface SpringDataDishRepository extends JpaRepository<DishJpaEntity, UUID> {

    List<DishJpaEntity> findByRestaurantId(UUID restaurantId);

    List<DishJpaEntity> findByRestaurantIdAndLiveNameIsNotNull(UUID restaurantId);

    long countByRestaurantIdAndLiveNameIsNotNull(UUID restaurantId);

    List<DishJpaEntity> findByRestaurantIdAndDraftNameIsNotNull(UUID restaurantId);

    List<DishJpaEntity> findByDraftNameIsNotNullAndScheduledAtIsNotNullAndScheduledAtLessThanEqual(LocalDateTime now);
}
