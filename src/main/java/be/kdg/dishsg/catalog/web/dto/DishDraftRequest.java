package be.kdg.dishsg.catalog.web.dto;

import java.util.List;
import java.util.UUID;

public class DishDraftRequest {
    public UUID id;            // null = create new draft, non-null = update existing draft
    public UUID restaurantId;  // required when creating
    public String name;
    public String type;        // STARTER | MAIN | DESSERT
    public List<String> foodTags;
    public String description;
    public double price;
    public String pictureUrl;
}
