package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.domain.exception.RestaurantNotFoundException;
import be.sebastiangondek.kdg.restaurants.ports.in.OpenCloseRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

// Service for manually opening or closing a restaurant.
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
