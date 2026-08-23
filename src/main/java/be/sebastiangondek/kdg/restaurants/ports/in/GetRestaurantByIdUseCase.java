package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

import java.util.Optional;

// In-port for fetching a single restaurant by its UUID string.
public interface GetRestaurantByIdUseCase {
    Optional<Restaurant> getRestaurantById(String id);
}
