package be.kdg.dishsg.catalog.web.dto;

import java.util.List;
import java.util.UUID;

public class DishResponse {
    public UUID id;
    public UUID restaurantId;
    public String name;
    public String type;
    public List<String> foodTags;
    public String description;
    public double price;
    public String pictureUrl;
    public boolean inStock;
    public String state;
}
