package be.kdg.dishsg.order.ports.out;

import be.kdg.dishsg.order.domain.Order;

public interface SaveOrderPort {
    Order save(Order order);
}
