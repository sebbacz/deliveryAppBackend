package be.kdg.dishsg.order.web.dto;

public class BusynessResponse {
    public int pendingOrderCount;

    public BusynessResponse(int pendingOrderCount) {
        this.pendingOrderCount = pendingOrderCount;
    }
}
