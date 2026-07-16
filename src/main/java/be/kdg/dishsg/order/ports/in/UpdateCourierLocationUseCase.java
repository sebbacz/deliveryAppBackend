package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

// In-port for updating the live GPS position of the courier delivering an order.
public interface UpdateCourierLocationUseCase {
    void updateCourierLocation(UUID orderId, double latitude, double longitude);
}
