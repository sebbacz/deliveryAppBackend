package be.kdg.dishsg.restaurant.app;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;

import java.util.UUID;

public class CreateRestaurantService implements CreateRestaurantUseCase {

    private final RestaurantRepositoryPort restaurantRepository;

    public CreateRestaurantService(RestaurantRepositoryPort restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
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
}
