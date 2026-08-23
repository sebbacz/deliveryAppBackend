package be.sebastiangondek.kdg.restaurants.ports.in;

import java.util.UUID;

// In-port for  publishing all pending dish drafts for a restaurant.
public interface ApplyPendingChangesUseCase {
    void applyPendingChanges(UUID restaurantId);
}
