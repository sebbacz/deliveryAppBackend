package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.ports.in.GetMyRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

// Returns the single restaurant for the authenticated owner; used by the owner dashboard -> create-restaurant.
@Transactional(readOnly = true)
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
