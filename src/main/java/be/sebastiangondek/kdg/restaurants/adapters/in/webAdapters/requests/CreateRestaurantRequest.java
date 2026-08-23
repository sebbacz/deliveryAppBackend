package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.requests;

import java.util.ArrayList;
import java.util.List;

//  request DTO for POST /api/restaurants; mapped to CreateRestaurantCmd by the controller.
public class CreateRestaurantRequest {

    public String name;
    public String street;
    public String number;
    public String postalCode;
    public String city;
    public String country;
    public String contactEmail;
    public List<String> pictureUrls = new ArrayList<>();
    public int defaultPreparationTime;
    public String typeOfCuisine;
    public String openingHours;
}
