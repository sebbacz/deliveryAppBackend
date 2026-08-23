package be.sebastiangondek.kdg.restaurants.ports.in;

import java.time.LocalDateTime;
import java.util.UUID;

// Command that schedules all pending drafts for a restaurant to go live at scheduledAt.
public record ScheduleDishChangesCmd(UUID restaurantId, LocalDateTime scheduledAt) {}
