package be.kdg.dishsg.restaurant.adapters.in.dto;

// Response DTO for a restaurant including address, opening hours, geo-coordinates, and open status.
public class RestaurantResponse {
    public String id;
    public String name;
    public String street;
    public String number;
    public String postalCode;
    public String city;
    public String country;
    public String contactEmail;
    public String pictureUrl;
    public int defaultPreparationTime;
    public String typeOfCuisine;
    public String openingHours;
    public boolean isOpen;
    public Double latitude;
    public Double longitude;
}
