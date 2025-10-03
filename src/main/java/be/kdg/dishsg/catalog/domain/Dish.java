package be.kdg.dishsg.catalog.domain;


import java.math.BigDecimal;
import java.util.UUID;

public class Dish {
    private final UUID id;
    private final UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private DishState state;
    private boolean inStock;

    public Dish(UUID id, UUID restaurantId, String name, String description, BigDecimal price, DishState state, boolean inStock) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
        this.inStock = inStock;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DishState getState() {
        return state;
    }

    public void setState(DishState state) {
        this.state = state;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
