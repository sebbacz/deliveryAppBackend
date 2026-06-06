package be.kdg.dishsg.restaurant.infra.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class RestaurantJpaEntity {

    @Id
    private String id;
    @Column(unique = true)
    private String ownerId;
    private String name;
    private String contactEmail;
    private String pictureUrl;
    private int defaultPreparationTime;
    private String typeOfCuisine;
    private String openingHours;
    private boolean isOpen = true;
    private Double latitude;
    private Double longitude;

    @Embedded
    private AddressEmbeddable address;

    public RestaurantJpaEntity() {}

    public RestaurantJpaEntity(String id, String ownerId, String name, AddressEmbeddable address,
                               String contactEmail, String pictureUrl, int defaultPreparationTime,
                               String typeOfCuisine, String openingHours, boolean isOpen,
                               Double latitude, Double longitude) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.pictureUrl = pictureUrl;
        this.defaultPreparationTime = defaultPreparationTime;
        this.typeOfCuisine = typeOfCuisine;
        this.openingHours = openingHours;
        this.isOpen = isOpen;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public AddressEmbeddable getAddress() {
        return address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
