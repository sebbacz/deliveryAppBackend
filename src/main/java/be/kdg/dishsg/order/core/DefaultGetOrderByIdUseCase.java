package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.GetOrderByIdUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DefaultGetOrderByIdUseCase implements GetOrderByIdUseCase {

    private final OrderRepositoryPort repository;

    public DefaultGetOrderByIdUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Order getOrderById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
