package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.ports.in.DeleteRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Service for removing a restaurant by its owner ID.
@Transactional
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
