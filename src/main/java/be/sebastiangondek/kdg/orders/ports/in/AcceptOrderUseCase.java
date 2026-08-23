package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port for accepting a pending order and notifying the delivery service.
public interface AcceptOrderUseCase {
    void acceptOrder(AcceptOrderCmd cmd);
}
