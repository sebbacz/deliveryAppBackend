package be.kdg.dishsg.restaurant.ports.in;

public record CreateRestaurantCmd(
        String ownerId,
        String name,
        String street,
        String number,
        String postalCode,
        String city,
        String country,
        String contactEmail,
        String pictureUrl,
        int defaultPreparationTime,
        String typeOfCuisine,
        String openingHours
) {}
