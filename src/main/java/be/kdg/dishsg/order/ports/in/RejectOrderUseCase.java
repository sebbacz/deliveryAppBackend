package be.kdg.dishsg.order.ports.in;

public interface RejectOrderUseCase {
    void rejectOrder(RejectOrderCmd cmd);
}
