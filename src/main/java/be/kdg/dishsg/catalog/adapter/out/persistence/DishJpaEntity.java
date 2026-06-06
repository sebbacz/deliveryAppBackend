package be.kdg.dishsg.catalog.adapter.out.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dishes")
public class DishJpaEntity {

    @Id
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(nullable = false)
    private String name;

    private String type;

    @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
    @CollectionTable(name = "dish_food_tags", joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "tag")
    private List<String> foodTags;

    private String description;

    @Column(nullable = false)
    private double price;

    private String pictureUrl;

    @Column(nullable = false)
    private boolean inStock = true;

    @Column(nullable = false)
    private String state;

    private LocalDateTime scheduledAt;

    protected DishJpaEntity() {}

    public DishJpaEntity(UUID id, UUID restaurantId, String name, String type, List<String> foodTags,
                         String description, double price, String pictureUrl, boolean inStock, String state,
                         LocalDateTime scheduledAt) {
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
        this.scheduledAt = scheduledAt;
    }

    public UUID getId()                   { return id; }
    public UUID getRestaurantId()         { return restaurantId; }
    public String getName()               { return name; }
    public String getType()               { return type; }
    public List<String> getFoodTags()     { return foodTags; }
    public String getDescription()        { return description; }
    public double getPrice()              { return price; }
    public String getPictureUrl()         { return pictureUrl; }
    public boolean isInStock()            { return inStock; }
    public String getState()              { return state; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
}
