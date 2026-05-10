package be.kdg.dishsg.catalog.ports.out;

import be.kdg.dishsg.catalog.domain.Dish;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishRepositoryPort {
    Dish save(Dish dish);
    Optional<Dish> findById(UUID id);
    List<Dish> findAllByRestaurantId(UUID restaurantId);
    List<Dish> findAllDraftsByRestaurantId(UUID restaurantId);
    long countLiveByRestaurantId(UUID restaurantId);
    List<Dish> saveAll(List<Dish> dishes);
    List<Dish> findScheduledDraftsDue(LocalDateTime now);
}
