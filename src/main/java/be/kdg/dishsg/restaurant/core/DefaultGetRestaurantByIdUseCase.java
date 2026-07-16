package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.GetRestaurantByIdUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultGetRestaurantByIdUseCase implements GetRestaurantByIdUseCase {

    private final RestaurantRepositoryPort restaurantRepository;

    public DefaultGetRestaurantByIdUseCase(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }
}
