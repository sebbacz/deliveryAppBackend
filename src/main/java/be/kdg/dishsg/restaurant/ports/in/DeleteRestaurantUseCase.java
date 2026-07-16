package be.kdg.dishsg.restaurant.ports.in;

// In-port for deleting a restaurant by its owner's ID.
public interface DeleteRestaurantUseCase {
    void deleteRestaurantByOwnerId(String ownerId);
}
