package be.sebastiangondek.kdg.restaurants.domain;

import java.util.ArrayList;
import java.util.List;

// Restaurant
public class Restaurant {

    private String id;
    private String ownerId;
    private String name;
    private Address address;
    private String contactEmail;
    private List<String> pictureUrls;
    private int defaultPreparationTime;
    private String typeOfCuisine;
    private String openingHours;
    private boolean isOpen;
    private boolean manualOverride;
    private Double latitude;
    private Double longitude;

    public Restaurant() {}

    public Restaurant(String id, String ownerId, String name, Address address, String contactEmail,
                      List<String> pictureUrls, int defaultPreparationTime, String typeOfCuisine,
                      String openingHours, boolean isOpen, boolean manualOverride, Double latitude, Double longitude) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.pictureUrls = pictureUrls != null ? pictureUrls : new ArrayList<>();
        this.defaultPreparationTime = defaultPreparationTime;
        this.typeOfCuisine = typeOfCuisine;
        this.openingHours = openingHours;
        this.isOpen = isOpen;
        this.manualOverride = manualOverride;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId()                       { return id; }
    public String getOwnerId()                  { return ownerId; }
    public String getName()                     { return name; }
    public Address getAddress()                 { return address; }
    public String getContactEmail()             { return contactEmail; }
    public List<String> getPictureUrls()        { return pictureUrls != null ? pictureUrls : new ArrayList<>(); }
    public int getDefaultPreparationTime()      { return defaultPreparationTime; }
    public String getTypeOfCuisine()            { return typeOfCuisine; }
    public String getOpeningHours()             { return openingHours; }
    public boolean isOpen()                     { return isOpen; }
    public boolean isManualOverride()           { return manualOverride; }
    public Double getLatitude()                 { return latitude; }
    public Double getLongitude()                { return longitude; }

    // manualOverride=true tells the scheduler to leave this restaurant alone until the schedule agrees
    public void open()  { this.isOpen = true;  this.manualOverride = true; }
    public void close() { this.isOpen = false; this.manualOverride = true; }
    public void clearManualOverride() { this.manualOverride = false; }

    public void setLatitude(Double latitude)   { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
