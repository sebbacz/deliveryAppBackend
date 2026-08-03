package be.kdg.dishsg.restaurant.ports.out;

// Kept as a convenience interface combining the focused Load and Save ports.
public interface RestaurantRepositoryPort extends LoadRestaurantPort, SaveRestaurantPort {
    void deleteByOwnerId(String ownerId);
}
