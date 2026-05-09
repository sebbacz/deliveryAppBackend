package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

public interface UnpublishDishUseCase {
    void unpublishDish(UUID dishId);
}
