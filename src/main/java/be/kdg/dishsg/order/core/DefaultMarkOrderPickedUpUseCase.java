package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.MarkOrderPickedUpCmd;
import be.kdg.dishsg.order.ports.in.MarkOrderPickedUpUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

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
