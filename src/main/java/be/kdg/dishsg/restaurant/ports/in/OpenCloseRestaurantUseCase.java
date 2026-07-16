package be.kdg.dishsg.restaurant.ports.in;

// In-port for manually opening or closing a restaurant (sets a manual override).
public interface OpenCloseRestaurantUseCase {
    void openRestaurant(String ownerId);
    void closeRestaurant(String ownerId);
}
