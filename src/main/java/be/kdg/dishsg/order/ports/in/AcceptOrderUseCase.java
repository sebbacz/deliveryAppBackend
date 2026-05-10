package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface AcceptOrderUseCase {
    void acceptOrder(UUID orderId);
}
