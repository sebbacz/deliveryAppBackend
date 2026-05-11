package be.kdg.dishsg.restaurant.ports.in;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.List;

public interface GetAllRestaurantsUseCase {
    List<Restaurant> getAllRestaurants();
}
