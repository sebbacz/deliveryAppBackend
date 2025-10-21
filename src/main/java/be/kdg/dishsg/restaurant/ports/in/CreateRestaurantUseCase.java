package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant createRestaurant(Restaurant restaurant);
}
