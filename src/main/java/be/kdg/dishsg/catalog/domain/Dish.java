package be.kdg.dishsg.catalog.domain;

import java.util.List;
import java.util.UUID;

public class Dish {

    public enum DishState { DRAFT, LIVE }

    private final UUID id;
    private final UUID restaurantId;
    private String name;
    private DishType type;
    private List<String> foodTags;
    private String description;
    private double price;
    private String pictureUrl;
    private boolean inStock;
    private DishState state;

    public Dish(UUID id, UUID restaurantId, String name, DishType type, List<String> foodTags,
                String description, double price, String pictureUrl, boolean inStock, DishState state) {
        if (restaurantId == null) throw new IllegalArgumentException("Restaurant ID required");
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.foodTags = foodTags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.inStock = inStock;
        this.state = state;
    }

    public void updateDraft(String name, DishType type, List<String> foodTags,
                            String description, double price, String pictureUrl) {
        if (state != DishState.DRAFT) throw new IllegalStateException("Only draft dishes can be edited");
        this.name = name;
        this.type = type;
        this.foodTags = foodTags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public void publish() {
        if (state == DishState.LIVE) throw new IllegalStateException("Dish is already published");
        this.state = DishState.LIVE;
    }

    public void unpublish() {
        if (state == DishState.DRAFT) throw new IllegalStateException("Dish is already a draft");
        this.state = DishState.DRAFT;
    }

    public void markOutOfStock() { this.inStock = false; }
    public void markInStock()    { this.inStock = true; }

    public UUID getId()               { return id; }
    public UUID getRestaurantId()     { return restaurantId; }
    public String getName()           { return name; }
    public DishType getType()         { return type; }
    public List<String> getFoodTags() { return foodTags; }
    public String getDescription()    { return description; }
    public double getPrice()          { return price; }
    public String getPictureUrl()     { return pictureUrl; }
    public boolean isInStock()        { return inStock; }
    public DishState getState()       { return state; }
}
