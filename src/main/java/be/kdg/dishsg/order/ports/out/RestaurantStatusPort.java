package be.kdg.dishsg.order.ports.out;

import java.util.UUID;

public interface RestaurantStatusPort {
    boolean isRestaurantOpen(UUID restaurantId);
}
