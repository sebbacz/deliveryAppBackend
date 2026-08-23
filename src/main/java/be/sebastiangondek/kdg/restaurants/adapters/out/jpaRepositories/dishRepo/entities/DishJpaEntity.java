package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.dishRepo.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// JPA entity for a dish.
@Entity
@Table(name = "dishes")
public class DishJpaEntity {

    @Id
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "in_stock", nullable = false)
    private boolean inStock = true;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    // Live version (null = not published)
    @Column(name = "live_name")
    private String liveName;
    @Column(name = "live_type")
    private String liveType;
    @Column(name = "live_description")
    private String liveDescription;
    @Column(name = "live_price")
    private Double livePrice;
    @Column(name = "live_picture_url")
    private String livePictureUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dish_live_food_tags", joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "tag")
    private List<String> liveFoodTags;

    // Draft version (null = no pending changes)
    @Column(name = "draft_name")
    private String draftName;
    @Column(name = "draft_type")
    private String draftType;
    @Column(name = "draft_description")
    private String draftDescription;
    @Column(name = "draft_price")
    private Double draftPrice;
    @Column(name = "draft_picture_url")
    private String draftPictureUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dish_draft_food_tags", joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "tag")
    private List<String> draftFoodTags;

    protected DishJpaEntity() {}

    public DishJpaEntity(UUID id, UUID restaurantId, boolean inStock, LocalDateTime scheduledAt,
                         String liveName, String liveType, List<String> liveFoodTags,
                         String liveDescription, Double livePrice, String livePictureUrl,
                         String draftName, String draftType, List<String> draftFoodTags,
                         String draftDescription, Double draftPrice, String draftPictureUrl) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.inStock = inStock;
        this.scheduledAt = scheduledAt;
        this.liveName = liveName;
        this.liveType = liveType;
        this.liveFoodTags = liveFoodTags;
        this.liveDescription = liveDescription;
        this.livePrice = livePrice;
        this.livePictureUrl = livePictureUrl;
        this.draftName = draftName;
        this.draftType = draftType;
        this.draftFoodTags = draftFoodTags;
        this.draftDescription = draftDescription;
        this.draftPrice = draftPrice;
        this.draftPictureUrl = draftPictureUrl;
    }

    public UUID getId()                   { return id; }
    public UUID getRestaurantId()         { return restaurantId; }
    public boolean isInStock()            { return inStock; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public String getLiveName()           { return liveName; }
    public String getLiveType()           { return liveType; }
    public List<String> getLiveFoodTags() { return liveFoodTags; }
    public String getLiveDescription()    { return liveDescription; }
    public Double getLivePrice()          { return livePrice; }
    public String getLivePictureUrl()     { return livePictureUrl; }
    public String getDraftName()          { return draftName; }
    public String getDraftType()          { return draftType; }
    public List<String> getDraftFoodTags(){ return draftFoodTags; }
    public String getDraftDescription()   { return draftDescription; }
    public Double getDraftPrice()         { return draftPrice; }
    public String getDraftPictureUrl()    { return draftPictureUrl; }
}
