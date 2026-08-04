package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.MarkOrderDeliveredCmd;
import be.kdg.dishsg.order.ports.in.MarkOrderDeliveredUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

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
