package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

// In-port for immediately publishing all pending dish drafts for a restaurant.
public interface ApplyPendingChangesUseCase {
    void applyPendingChanges(UUID restaurantId);
}
