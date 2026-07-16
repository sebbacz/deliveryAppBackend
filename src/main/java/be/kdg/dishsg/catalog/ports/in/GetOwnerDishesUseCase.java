package be.kdg.dishsg.catalog.ports.in;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.List;
import java.util.UUID;

// In-port for fetching all dishes (any state) belonging to a restaurant for the owner view.
public interface GetOwnerDishesUseCase {
    List<Dish> getOwnerDishes(UUID restaurantId);
}
