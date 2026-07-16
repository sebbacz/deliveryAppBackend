package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

// In-port for moving a LIVE dish back to DRAFT state.
public interface UnpublishDishUseCase {
    void unpublishDish(UUID dishId);
}
