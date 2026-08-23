package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.restaurantRepo.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// JPA entity for a restaurant; prevents the scheduler from overriding owner open/close actions.
@Entity
@Table(name = "restaurants")
public class RestaurantJpaEntity {

    @Id
    private String id;
    @Column(unique = true)
    private String ownerId;
    private String name;
    private String contactEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurant_picture_urls", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "picture_url")
    private List<String> pictureUrls = new ArrayList<>();

    private int defaultPreparationTime;
    private String typeOfCuisine;
    private String openingHours;
    private boolean isOpen = true;
    @Column(name = "manual_override", nullable = false, columnDefinition = "boolean default false")
    private boolean manualOverride = false;
    private Double latitude;
    private Double longitude;

    @Embedded
    private AddressEmbeddable address;

    public RestaurantJpaEntity() {}

    public RestaurantJpaEntity(String id, String ownerId, String name, AddressEmbeddable address,
                               String contactEmail, List<String> pictureUrls, int defaultPreparationTime,
                               String typeOfCuisine, String openingHours, boolean isOpen,
                               boolean manualOverride, Double latitude, Double longitude) {
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

    public String getId()                    { return id; }
    public String getOwnerId()               { return ownerId; }
    public String getName()                  { return name; }
    public String getContactEmail()          { return contactEmail; }
    public List<String> getPictureUrls()     { return pictureUrls; }
    public int getDefaultPreparationTime()   { return defaultPreparationTime; }
    public String getTypeOfCuisine()         { return typeOfCuisine; }
    public String getOpeningHours()          { return openingHours; }
    public AddressEmbeddable getAddress()    { return address; }
    public boolean isOpen()                  { return isOpen; }
    public boolean isManualOverride()        { return manualOverride; }
    public Double getLatitude()              { return latitude; }
    public Double getLongitude()             { return longitude; }
}
