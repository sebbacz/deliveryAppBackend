package be.kdg.dishsg.catalog.domain;

public class Dish {

    public enum DishState { DRAFT, LIVE }

    private final String id;
    private final String restaurantId;
    private String name;
    private String description;
    private double price;
    private DishState state;

    public Dish(String id, String restaurantId, String name, String description, double price, DishState state) {
        if (restaurantId == null || restaurantId.isBlank()) throw new IllegalArgumentException("Restaurant ID required");
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.state = state;
    }

    public void updateDraft(String name, String description, double price) {
        if (state != DishState.DRAFT) throw new IllegalStateException("Only draft can be updated");
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void publish() {
        if (state == DishState.LIVE) throw new IllegalStateException("Already published");
        this.state = DishState.LIVE;
    }

    public void unpublish() {
        if (state == DishState.DRAFT) throw new IllegalStateException("Already draft");
        this.state = DishState.DRAFT;
    }

    public String getId() { return id; }
    public String getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public DishState getState() { return state; }
}
