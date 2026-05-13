package be.kdg.dishsg.order.ports.in;

import be.kdg.dishsg.order.domain.Order;

import java.util.UUID;

public interface GetOrderByIdUseCase {
    Order getOrderById(UUID id);
}
