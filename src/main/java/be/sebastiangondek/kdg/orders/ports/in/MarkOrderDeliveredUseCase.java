package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port for the terminal delivery confirmation
public interface MarkOrderDeliveredUseCase {
    void markOrderDelivered(MarkOrderDeliveredCmd cmd);
}
