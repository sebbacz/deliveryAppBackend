package be.sebastiangondek.kdg.orders.domain.exception;

import java.util.UUID;

public class RestaurantClosedException extends RuntimeException {
    public RestaurantClosedException(UUID restaurantId) {
        super("Restaurant is currently closed: " + restaurantId);
    }
}
