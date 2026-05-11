package be.kdg.dishsg.catalog.ports.in;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.List;
import java.util.UUID;

public interface GetPublishedDishesUseCase {
    List<Dish> getPublishedDishes(UUID restaurantId);
}
