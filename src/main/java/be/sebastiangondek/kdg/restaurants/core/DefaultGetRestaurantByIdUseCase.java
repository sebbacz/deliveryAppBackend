package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.ports.in.GetRestaurantByIdUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

// Used by both the customer detail page
@Transactional(readOnly = true)
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
