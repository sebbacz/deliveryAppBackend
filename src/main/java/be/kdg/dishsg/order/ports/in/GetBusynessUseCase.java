package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for checking how many active (pending or accepted) orders a restaurant currently has.
public interface GetBusynessUseCase {
    int getActiveOrderCount(UUID restaurantId);
}
