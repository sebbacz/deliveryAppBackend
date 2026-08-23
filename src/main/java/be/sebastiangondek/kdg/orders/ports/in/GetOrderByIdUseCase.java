package be.sebastiangondek.kdg.orders.ports.in;

import be.sebastiangondek.kdg.orders.domain.Order;

import java.util.UUID;

// In-port for loading a single order by its UUID.
public interface GetOrderByIdUseCase {
    Order getOrderById(UUID id);
}
