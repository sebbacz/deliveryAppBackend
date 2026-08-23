package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

import java.util.List;

// In-port for listing every restaurant in the system.
public interface GetAllRestaurantsUseCase {
    List<Restaurant> getAllRestaurants();
}
