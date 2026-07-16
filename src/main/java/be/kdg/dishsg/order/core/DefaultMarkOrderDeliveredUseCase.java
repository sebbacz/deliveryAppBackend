package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.MarkOrderDeliveredUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DefaultMarkOrderDeliveredUseCase implements MarkOrderDeliveredUseCase {

    private final OrderRepositoryPort repository;

    public DefaultMarkOrderDeliveredUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void markOrderDelivered(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.markDelivered();
        repository.save(order);
    }
}
