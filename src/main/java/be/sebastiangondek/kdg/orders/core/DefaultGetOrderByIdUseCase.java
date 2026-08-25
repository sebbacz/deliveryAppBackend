package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.GetOrderByIdUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

//  used by the public tracking page (no auth).
@Transactional(readOnly = true)
@Service
public class DefaultGetOrderByIdUseCase implements GetOrderByIdUseCase {

    private final OrderRepositoryPort repository;

    public DefaultGetOrderByIdUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Order getOrderById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
