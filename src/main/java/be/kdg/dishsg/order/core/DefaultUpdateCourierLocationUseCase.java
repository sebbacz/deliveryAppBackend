package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.UpdateCourierLocationUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DefaultUpdateCourierLocationUseCase implements UpdateCourierLocationUseCase {

    private final OrderRepositoryPort repository;

    public DefaultUpdateCourierLocationUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void updateCourierLocation(UUID orderId, double latitude, double longitude) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.updateCourierLocation(latitude, longitude);
        repository.save(order);
    }
}
