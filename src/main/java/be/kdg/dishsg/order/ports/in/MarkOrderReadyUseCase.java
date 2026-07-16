package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for marking an accepted order as ready for courier pickup.
public interface MarkOrderReadyUseCase {
    void markOrderReady(UUID orderId);
}
