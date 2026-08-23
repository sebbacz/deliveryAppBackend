package be.sebastiangondek.kdg.restaurants.ports.in;

import java.util.List;

// Command for registering a new restaurant; triggers map api geocoding in DefaultCreateRestaurantUseCase.
public record CreateRestaurantCmd(
        String ownerId,
        String name,
        String street,
        String number,
        String postalCode,
        String city,
        String country,
        String contactEmail,
        List<String> pictureUrls,
        int defaultPreparationTime,
        String typeOfCuisine,
        String openingHours
) {}
