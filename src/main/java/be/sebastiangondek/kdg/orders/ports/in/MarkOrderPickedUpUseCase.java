package be.sebastiangondek.kdg.orders.ports.in;

//  port triggered by the delivery service RabbitMQ message when the courier collects the order.
public interface MarkOrderPickedUpUseCase {
    void markOrderPickedUp(MarkOrderPickedUpCmd cmd);
}
