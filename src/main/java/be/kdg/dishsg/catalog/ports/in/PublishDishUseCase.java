package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

public interface PublishDishUseCase {
    void publishDish(UUID dishId);
}
