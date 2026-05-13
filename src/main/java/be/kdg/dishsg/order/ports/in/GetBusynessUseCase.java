package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface GetBusynessUseCase {
    int getActiveOrderCount(UUID restaurantId);
}
