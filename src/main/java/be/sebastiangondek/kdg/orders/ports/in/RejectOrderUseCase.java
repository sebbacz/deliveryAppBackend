package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port for rejecting a pending order with a mandatory reason.
public interface RejectOrderUseCase {
    void rejectOrder(RejectOrderCmd cmd);
}
