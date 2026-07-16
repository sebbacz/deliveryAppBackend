package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.GetMyRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultGetMyRestaurantUseCase implements GetMyRestaurantUseCase {

    private final RestaurantRepositoryPort restaurantRepository;

    public DefaultGetMyRestaurantUseCase(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Optional<Restaurant> getRestaurantByOwnerId(String ownerId) {
        return restaurantRepository.findByOwnerId(ownerId).stream().findFirst();
    }
}
