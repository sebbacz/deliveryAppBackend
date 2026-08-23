package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

import java.util.List;
import java.util.UUID;

// In-port for fetching all dishes
public interface GetOwnerDishesUseCase {
    List<Dish> getOwnerDishes(UUID restaurantId);
}
