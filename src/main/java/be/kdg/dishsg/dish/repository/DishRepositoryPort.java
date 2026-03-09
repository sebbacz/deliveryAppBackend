package be.kdg.dishsg.dish.repository;

import be.kdg.dishsg.dish.domain.Dish;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishRepositoryPort {

    Optional<Dish> findById(UUID id);
    void save(Dish dish);
    List<Dish> findAllDraftsByRestaurant(UUID restaurantId);
    long countLiveByRestaurant(UUID restaurantId);
    void saveAll(List<Dish> dishes);
}
