package be.kdg.dishsg.restaurant.adapters.in.webAdapters.requests;

// Request body for creating a new restaurant, including address and opening hours.
public class CreateRestaurantRequest {

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
}
