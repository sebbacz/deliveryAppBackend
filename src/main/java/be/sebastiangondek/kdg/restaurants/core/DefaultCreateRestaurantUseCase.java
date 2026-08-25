package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Address;
import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.ports.in.CreateRestaurantCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.CreateRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.GeocodingPort;
import be.sebastiangondek.kdg.restaurants.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// One owner → one restaurant rule enforced.
@Transactional
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
                false,
                coords != null ? coords[0] : null,
                coords != null ? coords[1] : null
        );
        return restaurantRepository.save(toSave);
    }
}
