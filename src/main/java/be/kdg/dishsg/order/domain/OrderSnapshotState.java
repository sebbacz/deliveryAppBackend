package be.kdg.dishsg.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Serialisable snapshot of an Order aggregate at a given event-stream version.
 * Stored as JSON in order_snapshots; used to avoid replaying the entire event
 * history on every load.
 */
public record OrderSnapshotState(
        UUID id,
        UUID restaurantId,
        String customerName,
        String deliveryStreet,
        String deliveryNumber,
        String deliveryPostalCode,
        String deliveryCity,
        String deliveryCountry,
        String contactEmail,
        List<ItemData> items,
        LocalDateTime createdAt,
        String status,
        String rejectionReason,
        Double courierLatitude,
        Double courierLongitude,
        long version
) {
    public record ItemData(UUID id, UUID dishId, String dishName, double price, int quantity) {}
}
