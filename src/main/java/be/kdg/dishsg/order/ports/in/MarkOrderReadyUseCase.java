package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface MarkOrderReadyUseCase {
    void markOrderReady(UUID orderId);
}
