package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.ports.in.DeleteRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

// Service for removing a restaurant by its owner's ID.
@Service
public class DefaultDeleteRestaurantUseCase implements DeleteRestaurantUseCase {

    private final RestaurantRepositoryPort repository;

    public DefaultDeleteRestaurantUseCase(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void deleteRestaurantByOwnerId(String ownerId) {
        repository.deleteByOwnerId(ownerId);
    }
}
