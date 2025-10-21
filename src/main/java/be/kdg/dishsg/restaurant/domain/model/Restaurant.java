package be.kdg.dishsg.restaurant.domain.model;

public class Restaurant {

    private String id;
    private String ownerId;
    private String name;
    private Address address;
    private String contactEmail;
    private String pictureUrl;
    private int defaultPreparationTime;
    private String typeOfCuisine;
    private String openingHours;

    public Restaurant(){}

    public Restaurant(String id, String ownerId, String name, Address address, String contactEmail, String pictureUrl, int defaultPreparationTime, String typeOfCuisine, String openingHours) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.pictureUrl = pictureUrl;
        this.defaultPreparationTime = defaultPreparationTime;
        this.typeOfCuisine = typeOfCuisine;
        this.openingHours = openingHours;
    }


    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getDefaultPreparationTime() {
        return defaultPreparationTime;
    }

    public String getTypeOfCuisine() {
        return typeOfCuisine;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}
