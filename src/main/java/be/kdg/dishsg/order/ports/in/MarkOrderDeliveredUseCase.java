package be.kdg.dishsg.order.ports.in;

public interface MarkOrderDeliveredUseCase {
    void markOrderDelivered(MarkOrderDeliveredCmd cmd);
}
