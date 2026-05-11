package be.kdg.dishsg.restaurant.app;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetAllRestaurantsUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetMyRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetRestaurantByIdUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CreateRestaurantService implements CreateRestaurantUseCase, GetMyRestaurantUseCase,
        GetAllRestaurantsUseCase, GetRestaurantByIdUseCase {

    private final RestaurantRepositoryPort restaurantRepository;

    public CreateRestaurantService(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (!restaurantRepository.findByOwnerId(restaurant.getOwnerId()).isEmpty()) {
            throw new IllegalStateException("Owner already has a restaurant");
        }
        Restaurant toSave = new Restaurant(
                UUID.randomUUID().toString(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getContactEmail(),
                restaurant.getPictureUrl(),
                restaurant.getDefaultPreparationTime(),
                restaurant.getTypeOfCuisine(),
                restaurant.getOpeningHours()
        );
        return restaurantRepository.save(toSave);
    }

    @Override
    public Optional<Restaurant> getRestaurantByOwnerId(String ownerId) {
        return restaurantRepository.findByOwnerId(ownerId).stream().findFirst();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }
}
