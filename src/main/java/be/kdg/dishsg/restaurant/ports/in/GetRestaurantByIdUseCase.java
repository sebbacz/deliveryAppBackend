package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.Optional;

// In-port for fetching a single restaurant by its UUID string.
public interface GetRestaurantByIdUseCase {
    Optional<Restaurant> getRestaurantById(String id);
}
