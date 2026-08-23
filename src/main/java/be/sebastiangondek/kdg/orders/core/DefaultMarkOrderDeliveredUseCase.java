package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

// once DELIVERED ->>polling on the tracking page stops.
@Service
public class DefaultMarkOrderDeliveredUseCase implements MarkOrderDeliveredUseCase {

    private final OrderRepositoryPort repository;

    public DefaultMarkOrderDeliveredUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void markOrderDelivered(MarkOrderDeliveredCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.markDelivered();
        repository.save(order);
    }
}
