package be.kdg.dishsg.dish.domain;

import jakarta.persistence.*;

import java.util.UUID;



@Entity
@Table(name = "dishes")
public class Dish {


    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private double price;
    private boolean draft;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    private boolean inStock;

    public Dish() {}

    public Dish(String name, String description, double price, UUID restaurantId, boolean draft, boolean inStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
        this.draft = draft;
        this.inStock = inStock;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public UUID getRestaurantId() { return restaurantId; }
    public boolean isDraft() { return draft; }
    public boolean isInStock() { return inStock; }

    public void setInStock(boolean inStock) { this.inStock = inStock; }
    public void setDraft(boolean draft) { this.draft = draft; }
}
