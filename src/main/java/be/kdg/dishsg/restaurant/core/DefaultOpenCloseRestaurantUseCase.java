package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.domain.exception.RestaurantNotFoundException;
import be.kdg.dishsg.restaurant.ports.in.OpenCloseRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

// Service for manually opening or closing a restaurant, setting the manual-override flag.
@Service
public class DefaultOpenCloseRestaurantUseCase implements OpenCloseRestaurantUseCase {

    private final RestaurantRepositoryPort repository;

    public DefaultOpenCloseRestaurantUseCase(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void openRestaurant(String ownerId) {
        Restaurant restaurant = repository.findByOwnerId(ownerId).stream().findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found for owner: " + ownerId));
        restaurant.open();
        repository.save(restaurant);
    }

    @Override
    public void closeRestaurant(String ownerId) {
        Restaurant restaurant = repository.findByOwnerId(ownerId).stream().findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found for owner: " + ownerId));
        restaurant.close();
        repository.save(restaurant);
    }
}
