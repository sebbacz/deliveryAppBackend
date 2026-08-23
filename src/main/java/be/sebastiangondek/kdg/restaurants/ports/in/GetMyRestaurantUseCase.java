package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

import java.util.Optional;

// In-port for fetching the restaurant owned by a specific owner.
public interface GetMyRestaurantUseCase {
    Optional<Restaurant> getRestaurantByOwnerId(String ownerId);
}
