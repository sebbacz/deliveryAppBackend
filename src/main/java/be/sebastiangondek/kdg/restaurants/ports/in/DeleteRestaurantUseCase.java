package be.sebastiangondek.kdg.restaurants.ports.in;

// In-port for deleting a restaurant by its owner id port
public interface DeleteRestaurantUseCase {
    void deleteRestaurantByOwnerId(String ownerId);
}
