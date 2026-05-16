package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public interface UpdateCourierLocationUseCase {
    void updateCourierLocation(UUID orderId, double latitude, double longitude);
}
