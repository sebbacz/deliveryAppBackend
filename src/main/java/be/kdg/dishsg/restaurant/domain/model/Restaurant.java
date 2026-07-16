package be.kdg.dishsg.restaurant.domain.model;

// Domain model for a restaurant, including open/close state, manual override flag, and geo-coordinates.
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
    private boolean manualOverride;
    private Double latitude;
    private Double longitude;

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

    public Restaurant(String id, String ownerId, String name, Address address, String contactEmail, String pictureUrl, int defaultPreparationTime, String typeOfCuisine, String openingHours, boolean isOpen, Double latitude, Double longitude) {
        this(id, ownerId, name, address, contactEmail, pictureUrl, defaultPreparationTime, typeOfCuisine, openingHours, isOpen);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurant(String id, String ownerId, String name, Address address, String contactEmail, String pictureUrl, int defaultPreparationTime, String typeOfCuisine, String openingHours, boolean isOpen, boolean manualOverride, Double latitude, Double longitude) {
        this(id, ownerId, name, address, contactEmail, pictureUrl, defaultPreparationTime, typeOfCuisine, openingHours, isOpen, latitude, longitude);
        this.manualOverride = manualOverride;
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
        this.manualOverride = true;
    }

    public void close() {
        this.isOpen = false;
        this.manualOverride = true;
    }

    public void clearManualOverride() {
        this.manualOverride = false;
    }

    public boolean isManualOverride() {
        return manualOverride;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
