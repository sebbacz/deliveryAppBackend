package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.Optional;

// In-port for fetching the restaurant owned by a specific owner.
public interface GetMyRestaurantUseCase {
    Optional<Restaurant> getRestaurantByOwnerId(String ownerId);
}
