package be.kdg.dishsg.restaurant.domain;

import java.util.UUID;

public class Restaurant {
    private final UUID id;
    private final Owner owner;
    private final String name;
    private final String street;
    private final String number;
    private final String postalCode;
    private final String city;
    private final String country;
    private final String contactEmail;
    private final String pictureUrl;
    private final String cuisineType;
    private final Integer defaultPrepTime;
    private final String openingHours;

    public Restaurant(UUID id, Owner owner, String name, String street, String number, String postalCode, String city, String country, String contactEmail, String pictureUrl, String cuisineType, Integer defaultPrepTime, String openingHours) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.contactEmail = contactEmail;
        this.pictureUrl = pictureUrl;
        this.cuisineType = cuisineType;
        this.defaultPrepTime = defaultPrepTime;
        this.openingHours = openingHours;
    }
}
