package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.RejectOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.RejectOrderUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

// Rejects an order with a mandatory reason; the reason is stored in the event log and shown on the tracking page.
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
