package be.kdg.dishsg.domain.Restaurant;

import java.util.UUID;

public class Restaurant {

    private final UUID id;
    private final UUID ownerId;
    private String name;
    private String address;
    private String cuisineType;
    private OpeningHours openingHours;
    private OperationalStatus operationalStatus;


    public Restaurant(UUID id, UUID ownerId, String name, String address, String cuisineType, OpeningHours openingHours, OperationalStatus operationalStatus) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.operationalStatus = operationalStatus;
    }
}
