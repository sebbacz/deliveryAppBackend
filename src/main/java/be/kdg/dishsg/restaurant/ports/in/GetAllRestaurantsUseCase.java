package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.List;

// In-port for listing every restaurant in the system.
public interface GetAllRestaurantsUseCase {
    List<Restaurant> getAllRestaurants();
}
