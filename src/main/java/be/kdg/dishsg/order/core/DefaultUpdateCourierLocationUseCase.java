package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.UpdateCourierLocationCmd;
import be.kdg.dishsg.order.ports.in.UpdateCourierLocationUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

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
