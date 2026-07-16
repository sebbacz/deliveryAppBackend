package be.kdg.dishsg.order.adapters.in.dto;

// Response DTO reporting the number of currently active orders at a restaurant.
public class BusynessResponse {
    public int pendingOrderCount;

    public BusynessResponse(int pendingOrderCount) {
        this.pendingOrderCount = pendingOrderCount;
    }
}
