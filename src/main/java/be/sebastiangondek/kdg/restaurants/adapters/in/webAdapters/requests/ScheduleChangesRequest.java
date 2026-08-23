package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.requests;

import java.time.LocalDateTime;
import java.util.UUID;


// Inbound request DTO for POST /api/dishes/schedule.
public class ScheduleChangesRequest {
    public UUID restaurantId;
    public LocalDateTime scheduledAt;
}
