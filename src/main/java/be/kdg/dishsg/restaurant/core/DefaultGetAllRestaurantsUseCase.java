package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.GetAllRestaurantsUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetAllRestaurantsUseCase implements GetAllRestaurantsUseCase {

    private final RestaurantRepositoryPort restaurantRepository;

    public DefaultGetAllRestaurantsUseCase(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
