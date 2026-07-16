package be.kdg.dishsg.catalog.ports.in;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.List;
import java.util.UUID;

// In-port for fetching only LIVE dishes visible to customers on the public menu.
public interface GetPublishedDishesUseCase {
    List<Dish> getPublishedDishes(UUID restaurantId);
}
