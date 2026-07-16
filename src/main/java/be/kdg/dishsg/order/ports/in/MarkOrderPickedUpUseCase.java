package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for marking an order as picked up by the courier from the restaurant.
public interface MarkOrderPickedUpUseCase {
    void markOrderPickedUp(UUID orderId);
}
