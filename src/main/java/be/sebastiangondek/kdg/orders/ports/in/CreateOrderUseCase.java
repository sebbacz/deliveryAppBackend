package be.sebastiangondek.kdg.orders.ports.in;

import be.sebastiangondek.kdg.orders.domain.Order;

// Inbound port for placing a new order; validates restaurant open status before creation.
public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCmd cmd);
}
