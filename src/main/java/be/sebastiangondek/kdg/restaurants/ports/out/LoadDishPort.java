package be.sebastiangondek.kdg.restaurants.ports.out;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Out-port for reading dishes
public interface LoadDishPort {
    Optional<Dish> findById(UUID id);
    List<Dish> findAllByRestaurantId(UUID restaurantId);
    List<Dish> findLiveByRestaurantId(UUID restaurantId);
    List<Dish> findWithPendingDraftByRestaurantId(UUID restaurantId);
    long countLiveByRestaurantId(UUID restaurantId);
    List<Dish> findScheduledDraftsDue(LocalDateTime now);
}
