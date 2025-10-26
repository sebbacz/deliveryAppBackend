package be.kdg.dishsg.catalog.ports.out;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.Optional;

public interface DishRepositoryPort {
    Dish save(Dish dish);
    Optional<Dish> findById(String id);
}
