package be.sebastiangondek.kdg.restaurants.ports.in;

import java.util.UUID;

// In-port for moving a single dish from DRAFT to LIVE.
public interface PublishDishUseCase {
    void publishDish(UUID dishId);
}
