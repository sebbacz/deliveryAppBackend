package be.kdg.dishsg.order.ports.in;

import be.kdg.dishsg.order.domain.Order;

public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCmd cmd);
}
