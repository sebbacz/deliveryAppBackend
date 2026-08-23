package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderReadyCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderReadyUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

// Marks order ready and publishes RabbitMQ event so the delivery service knows to dispatch a courier.
@Service
public class DefaultMarkOrderReadyUseCase implements MarkOrderReadyUseCase {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public DefaultMarkOrderReadyUseCase(OrderRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void markOrderReady(MarkOrderReadyCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.markReady();
        repository.save(order);
        String routingKey = "restaurant." + order.getRestaurantId() + ".order.ready.v1";
        rabbitTemplate.convertAndSend("kdg.events", routingKey,
                Map.of("orderId", order.getId(), "restaurantId", order.getRestaurantId()));
    }
}
