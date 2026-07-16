package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.MarkOrderPickedUpUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DefaultMarkOrderPickedUpUseCase implements MarkOrderPickedUpUseCase {

    private final OrderRepositoryPort repository;

    public DefaultMarkOrderPickedUpUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void markOrderPickedUp(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.markPickedUp();
        repository.save(order);
    }
}
