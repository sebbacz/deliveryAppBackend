package be.sebastiangondek.kdg.restaurants.ports.in;

// In-port for manually opening or closing a restaurant
public interface OpenCloseRestaurantUseCase {
    void openRestaurant(String ownerId);
    void closeRestaurant(String ownerId);
}
