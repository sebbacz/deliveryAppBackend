package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface MarkOrderDeliveredUseCase {
    void markOrderDelivered(UUID orderId);
}
