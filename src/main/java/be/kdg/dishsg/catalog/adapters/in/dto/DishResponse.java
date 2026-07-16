package be.kdg.dishsg.catalog.adapters.in.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DishResponse {
    public UUID id;
    public UUID restaurantId;
    // display data (live if published, draft if draft-only)
    public String name;
    public String type;
    public List<String> foodTags;
    public String description;
    public double price;
    public String pictureUrl;
    // Stock and scheduling
    public boolean inStock;
    public String state; // "LIVE" | "DRAFT" | "LIVE_WITH_PENDING"
    public LocalDateTime scheduledAt;
    // Pending draft data (non-null when state == LIVE_WITH_PENDING)
    public PendingDraft pendingDraft;

    public static class PendingDraft {
        public String name;
        public String type;
        public List<String> foodTags;
        public String description;
        public double price;
        public String pictureUrl;
    }
}
