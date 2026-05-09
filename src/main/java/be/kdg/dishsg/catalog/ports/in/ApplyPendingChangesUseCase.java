package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

public interface ApplyPendingChangesUseCase {
    void applyPendingChanges(UUID restaurantId);
}
