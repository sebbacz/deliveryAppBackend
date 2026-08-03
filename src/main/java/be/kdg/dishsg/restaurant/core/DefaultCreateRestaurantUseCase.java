package be.kdg.dishsg.restaurant.core;

import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantCmd;
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
    public Restaurant createRestaurant(CreateRestaurantCmd cmd) {
        if (!restaurantRepository.findByOwnerId(cmd.ownerId()).isEmpty()) {
            throw new IllegalStateException("Owner already has a restaurant");
        }

        String fullAddress = cmd.street() + " " + cmd.number() + ", " +
                cmd.postalCode() + " " + cmd.city() + ", " + cmd.country();
        Double[] coords = geocodingPort.geocode(fullAddress);

        Restaurant toSave = new Restaurant(
                UUID.randomUUID().toString(),
                cmd.ownerId(),
                cmd.name(),
                new Address(cmd.street(), cmd.number(), cmd.postalCode(), cmd.city(), cmd.country()),
                cmd.contactEmail(),
                cmd.pictureUrls(),
                cmd.defaultPreparationTime(),
                cmd.typeOfCuisine(),
                cmd.openingHours(),
                true,
                coords != null ? coords[0] : null,
                coords != null ? coords[1] : null
        );
        return restaurantRepository.save(toSave);
    }
}
