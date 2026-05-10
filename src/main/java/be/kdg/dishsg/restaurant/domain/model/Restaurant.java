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
    private boolean isOpen;

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
        this.isOpen = true;
    }

    public Restaurant(String id, String ownerId, String name, Address address, String contactEmail, String pictureUrl, int defaultPreparationTime, String typeOfCuisine, String openingHours, boolean isOpen) {
        this(id, ownerId, name, address, contactEmail, pictureUrl, defaultPreparationTime, typeOfCuisine, openingHours);
        this.isOpen = isOpen;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }
}
