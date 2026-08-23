package be.sebastiangondek.kdg.orders.ports.in;

// Inbound port for telling  the kitchen is done; publishes RabbitMQ event so delivery dispatches a courier.
public interface MarkOrderReadyUseCase {
    void markOrderReady(MarkOrderReadyCmd cmd);
}
