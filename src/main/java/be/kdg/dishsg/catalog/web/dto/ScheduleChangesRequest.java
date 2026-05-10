package be.kdg.dishsg.catalog.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduleChangesRequest {
    public UUID restaurantId;
    public LocalDateTime scheduledAt;
}
