package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.MarkOrderReadyCmd;
import be.kdg.dishsg.order.ports.in.MarkOrderReadyUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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
