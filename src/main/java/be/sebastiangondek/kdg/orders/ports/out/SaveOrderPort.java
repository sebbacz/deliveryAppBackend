package be.sebastiangondek.kdg.orders.ports.out;

import be.sebastiangondek.kdg.orders.domain.Order;

// Out-port for persisting an order.
public interface SaveOrderPort {
    Order save(Order order);
}
