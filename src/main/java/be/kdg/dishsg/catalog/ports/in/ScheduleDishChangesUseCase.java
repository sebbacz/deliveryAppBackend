package be.kdg.dishsg.catalog.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ScheduleDishChangesUseCase {
    void scheduleChanges(UUID restaurantId, LocalDateTime scheduledAt);
}
