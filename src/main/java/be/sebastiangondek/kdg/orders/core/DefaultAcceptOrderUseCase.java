package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.ports.in.AcceptOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.AcceptOrderUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

// Accepts an order and publishes a RabbitMQ event so the delivery service can prepare for pickup.
@Service
public class DefaultAcceptOrderUseCase implements AcceptOrderUseCase {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public DefaultAcceptOrderUseCase(OrderRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void acceptOrder(AcceptOrderCmd cmd) {
        Order order = repository.findById(cmd.orderId())
                .orElseThrow(() -> new OrderNotFoundException(cmd.orderId()));
        order.accept();
        repository.save(order);
        String routingKey = "restaurant." + order.getRestaurantId() + ".order.accepted.v1";
        rabbitTemplate.convertAndSend("kdg.events", routingKey,
                Map.of("orderId", order.getId(), "restaurantId", order.getRestaurantId()));
    }
}
