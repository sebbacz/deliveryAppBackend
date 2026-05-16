package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface MarkOrderPickedUpUseCase {
    void markOrderPickedUp(UUID orderId);
}
