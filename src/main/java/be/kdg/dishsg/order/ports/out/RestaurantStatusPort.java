package be.kdg.dishsg.order.ports.out;

import java.util.UUID;

// Out-port for checking whether a restaurant is currently open before accepting an order.
public interface RestaurantStatusPort {
    boolean isRestaurantOpen(UUID restaurantId);
}
