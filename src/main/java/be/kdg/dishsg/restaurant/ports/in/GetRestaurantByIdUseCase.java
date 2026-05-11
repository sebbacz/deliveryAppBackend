package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.Optional;

public interface GetRestaurantByIdUseCase {
    Optional<Restaurant> getRestaurantById(String id);
}
