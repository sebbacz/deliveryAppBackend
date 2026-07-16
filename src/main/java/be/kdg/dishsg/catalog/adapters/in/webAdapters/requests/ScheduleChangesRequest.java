package be.kdg.dishsg.catalog.adapters.in.webAdapters.requests;

import java.time.LocalDateTime;
import java.util.UUID;


public class ScheduleChangesRequest {
    public UUID restaurantId;
    public LocalDateTime scheduledAt;
}
