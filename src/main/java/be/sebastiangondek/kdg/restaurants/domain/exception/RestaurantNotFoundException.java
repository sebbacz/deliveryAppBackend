package be.sebastiangondek.kdg.restaurants.domain.exception;

// Thrown when a restaurant lookup by ID or owner returns no result
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
