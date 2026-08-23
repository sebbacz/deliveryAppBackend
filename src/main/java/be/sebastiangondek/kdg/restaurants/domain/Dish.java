package be.sebastiangondek.kdg.restaurants.domain;

import java.time.LocalDateTime;
import java.util.UUID;

//  a single menu item; state  DRAFT/LIVE/LIVE_WITH_PENDING
public class Dish {

    public enum DishState { DRAFT, LIVE, LIVE_WITH_PENDING }

    private final UUID id;
    private final UUID restaurantId;
    private boolean inStock;
    private LocalDateTime scheduledAt;
    private DishData live;
    private DishData draft;

    public Dish(UUID id, UUID restaurantId, DishData live, DishData draft, boolean inStock, LocalDateTime scheduledAt) {
        if (restaurantId == null) throw new IllegalArgumentException("Restaurant ID required");
        if (live == null && draft == null) throw new IllegalArgumentException("Dish must have at least live or draft data");
        this.id = id;
        this.restaurantId = restaurantId;
        this.live = live;
        this.draft = draft;
        this.inStock = inStock;
        this.scheduledAt = scheduledAt;
    }

    public void saveDraft(String name, DishType type, java.util.List<String> foodTags,
                          String description, double price, String pictureUrl) {
        this.draft = new DishData(name, type, foodTags, description, price, pictureUrl);
    }

    public void publish() {
        if (draft == null) throw new IllegalStateException("No draft to publish");
        this.live = this.draft;
        this.draft = null;
        this.scheduledAt = null;
    }

    public void unpublish() {
        if (live == null) throw new IllegalStateException("Dish is not live");
        if (draft == null) this.draft = this.live; // preserve the live version as a draft so it isn't lost
        this.live = null;
    }

    public void schedulePublishAt(LocalDateTime at) {
        if (draft == null) throw new IllegalStateException("No draft to schedule");
        this.scheduledAt = at;
    }

    public void clearSchedule() { this.scheduledAt = null; }

    public void markOutOfStock() { this.inStock = false; }
    public void markInStock()    { this.inStock = true; }

    public DishState getState() {
        if (live != null && draft == null)  return DishState.LIVE;
        if (live == null && draft != null)  return DishState.DRAFT;
        return DishState.LIVE_WITH_PENDING;
    }

    public String getEffectiveName()        { return live != null ? live.name()        : draft.name(); }
    public DishType getEffectiveType()      { return live != null ? live.type()        : draft.type(); }
    public java.util.List<String> getEffectiveFoodTags() { return live != null ? live.foodTags() : draft.foodTags(); }
    public String getEffectiveDescription() { return live != null ? live.description() : draft.description(); }
    public double getEffectivePrice()       { return live != null ? live.price()       : draft.price(); }
    public String getEffectivePictureUrl()  { return live != null ? live.pictureUrl()  : draft.pictureUrl(); }

    public UUID getId()             { return id; }
    public UUID getRestaurantId()   { return restaurantId; }
    public boolean isInStock()      { return inStock; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public DishData getLive()       { return live; }
    public DishData getDraft()      { return draft; }
}
