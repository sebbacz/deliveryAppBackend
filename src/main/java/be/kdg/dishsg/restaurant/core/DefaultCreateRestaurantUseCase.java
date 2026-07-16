package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.out.GeocodingPort;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultCreateRestaurantUseCase implements CreateRestaurantUseCase {

    private final RestaurantRepositoryPort restaurantRepository;
    private final GeocodingPort geocodingPort;

    public DefaultCreateRestaurantUseCase(RestaurantRepositoryPort restaurantRepository, GeocodingPort geocodingPort) {
        this.restaurantRepository = restaurantRepository;
        this.geocodingPort = geocodingPort;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (!restaurantRepository.findByOwnerId(restaurant.getOwnerId()).isEmpty()) {
            throw new IllegalStateException("Owner already has a restaurant");
        }

        Address addr = restaurant.getAddress();
        String fullAddress = addr.getStreet() + " " + addr.getNumber() + ", " +
                addr.getPostalCode() + " " + addr.getCity() + ", " + addr.getCountry();
        Double[] coords = geocodingPort.geocode(fullAddress);

        Restaurant toSave = new Restaurant(
                UUID.randomUUID().toString(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getContactEmail(),
                restaurant.getPictureUrl(),
                restaurant.getDefaultPreparationTime(),
                restaurant.getTypeOfCuisine(),
                restaurant.getOpeningHours(),
                true,
                coords != null ? coords[0] : null,
                coords != null ? coords[1] : null
        );
        return restaurantRepository.save(toSave);
    }
}
