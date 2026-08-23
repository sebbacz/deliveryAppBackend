package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

import java.util.List;
import java.util.UUID;

// In-port for fetching only LIVE dishes visible to customers on the public menu.
public interface GetPublishedDishesUseCase {
    List<Dish> getPublishedDishes(UUID restaurantId);
}
