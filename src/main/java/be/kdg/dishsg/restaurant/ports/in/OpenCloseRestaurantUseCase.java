package be.kdg.dishsg.restaurant.ports.in;

public interface OpenCloseRestaurantUseCase {
    void openRestaurant(String ownerId);
    void closeRestaurant(String ownerId);
}
