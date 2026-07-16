package be.kdg.dishsg.catalog.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleDishChangesCmd(UUID restaurantId, LocalDateTime scheduledAt) {}
