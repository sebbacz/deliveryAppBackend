package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port for updating the live courier GPS position shown on the customer tracking map.
public interface UpdateCourierLocationUseCase {
    void updateCourierLocation(UpdateCourierLocationCmd cmd);
}
