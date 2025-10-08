package be.kdg.dishsg.restaurant.port.in;

import java.util.UUID;

public record CreateRestaurantCommand(UUID ownerId, String name, String street, String number, String postalCode, String city,
                                      String country, String contactEmail, String pictureUrl, String cuisineType,
                                      Integer defaultPrepTime, String openingHours) {


}
