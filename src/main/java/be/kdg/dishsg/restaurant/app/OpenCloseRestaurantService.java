package be.kdg.dishsg.restaurant.app;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.OpenCloseRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class OpenCloseRestaurantService implements OpenCloseRestaurantUseCase {

    private final RestaurantRepositoryPort repository;

    public OpenCloseRestaurantService(RestaurantRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void openRestaurant(String ownerId) {
        Restaurant restaurant = repository.findByOwnerId(ownerId).stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        restaurant.open();
        repository.save(restaurant);
    }

    @Override
    public void closeRestaurant(String ownerId) {
        Restaurant restaurant = repository.findByOwnerId(ownerId).stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        restaurant.close();
        repository.save(restaurant);
    }
}
