package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.AcceptOrderUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@Service
public class DefaultAcceptOrderUseCase implements AcceptOrderUseCase {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public DefaultAcceptOrderUseCase(OrderRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void acceptOrder(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.accept();
        repository.save(order);
        String routingKey = "restaurant." + order.getRestaurantId() + ".order.accepted.v1";
        rabbitTemplate.convertAndSend("kdg.events", routingKey,
                Map.of("orderId", order.getId(), "restaurantId", order.getRestaurantId()));
    }
}
