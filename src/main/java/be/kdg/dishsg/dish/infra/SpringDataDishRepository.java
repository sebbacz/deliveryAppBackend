package be.kdg.dishsg.dish.infra;

import be.kdg.dishsg.dish.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SpringDataDishRepository extends JpaRepository<Dish, UUID> {

    List<Dish> findByRestaurantIdAndDraftTrue(UUID restaurantId);
    long countByRestaurantIdAndDraftFalse(UUID restaurantId);
}
