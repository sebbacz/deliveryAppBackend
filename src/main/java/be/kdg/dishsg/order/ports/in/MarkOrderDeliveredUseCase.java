package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for marking an order as delivered by the courier.
public interface MarkOrderDeliveredUseCase {
    void markOrderDelivered(UUID orderId);
}
