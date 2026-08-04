package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.exception.OrderNotFoundException;
import be.kdg.dishsg.order.ports.in.AcceptOrderCmd;
import be.kdg.dishsg.order.ports.in.AcceptOrderUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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
