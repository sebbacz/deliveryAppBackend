package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(UUID orderId, String reason);
}
