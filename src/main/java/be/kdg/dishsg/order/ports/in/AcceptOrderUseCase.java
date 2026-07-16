package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for accepting a pending order.
public interface AcceptOrderUseCase {
    void acceptOrder(UUID orderId);
}
