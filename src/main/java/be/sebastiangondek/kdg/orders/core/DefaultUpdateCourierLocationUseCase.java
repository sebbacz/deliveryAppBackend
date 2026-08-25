package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.UpdateCourierLocationCmd;
import be.sebastiangondek.kdg.orders.ports.in.UpdateCourierLocationUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Platest GPS coordinates of ledlivery guy;
@Transactional
@Service
public class DefaultUpdateCourierLocationUseCase implements UpdateCourierLocationUseCase {

    private final OrderRepositoryPort repository;

    public DefaultUpdateCourierLocationUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void updateCourierLocation(UpdateCourierLocationCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.updateCourierLocation(cmd.latitude(), cmd.longitude());
        repository.save(order);
    }
}
