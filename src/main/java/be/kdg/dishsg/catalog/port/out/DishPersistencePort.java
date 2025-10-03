package be.kdg.dishsg.catalog.port.out;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishPersistencePort {
    Dish save(Dish dish);
    Optional<Dish> findById(UUID dishId);
    List<Dish> findByRestaurantId(UUID restaurantId);
    long countPublishedAndInStock(UUID restaurantId);
}
