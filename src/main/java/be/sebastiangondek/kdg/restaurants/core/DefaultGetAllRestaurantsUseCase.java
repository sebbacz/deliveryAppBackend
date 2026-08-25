package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.ports.in.GetAllRestaurantsUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// Returns all restaurants; used by the landing page.
@Transactional(readOnly = true)
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
