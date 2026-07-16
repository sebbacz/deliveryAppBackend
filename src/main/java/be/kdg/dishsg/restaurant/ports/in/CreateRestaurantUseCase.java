package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

// In-port for creating a new restaurant for an owner.
public interface CreateRestaurantUseCase {
    Restaurant createRestaurant(Restaurant restaurant);
}
