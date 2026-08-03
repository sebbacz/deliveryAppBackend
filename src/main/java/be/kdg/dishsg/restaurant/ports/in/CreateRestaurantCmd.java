package be.kdg.dishsg.restaurant.ports.in;

import java.util.List;

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
