package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

// Called by the delivery service via RabbitMQ when the courier collects the order from the restaurant.
@Service
public class DefaultMarkOrderPickedUpUseCase implements MarkOrderPickedUpUseCase {

    private final OrderRepositoryPort repository;

    public DefaultMarkOrderPickedUpUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void markOrderPickedUp(MarkOrderPickedUpCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.markPickedUp();
        repository.save(order);
    }
}
