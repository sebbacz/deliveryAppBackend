package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.RejectOrderCmd;
import be.kdg.dishsg.order.ports.in.RejectOrderUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DefaultRejectOrderUseCase implements RejectOrderUseCase {

    private final OrderRepositoryPort repository;

    public DefaultRejectOrderUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void rejectOrder(RejectOrderCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.reject(cmd.reason());
        repository.save(order);
    }
}
