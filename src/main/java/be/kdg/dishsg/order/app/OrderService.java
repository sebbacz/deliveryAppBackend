package be.kdg.dishsg.order.app;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderStatus;
import be.kdg.dishsg.order.ports.in.AcceptOrderUseCase;
import be.kdg.dishsg.order.ports.in.CreateOrderUseCase;
import be.kdg.dishsg.order.ports.in.GetOrdersUseCase;
import be.kdg.dishsg.order.ports.in.MarkOrderReadyUseCase;
import be.kdg.dishsg.order.ports.in.RejectOrderUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService implements AcceptOrderUseCase, RejectOrderUseCase,
        GetOrdersUseCase, CreateOrderUseCase, MarkOrderReadyUseCase {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Order createOrder(Order order) {
        Order toSave = new Order(
                UUID.randomUUID(),
                order.getRestaurantId(),
                order.getCustomerName(),
                order.getDeliveryStreet(),
                order.getDeliveryNumber(),
                order.getDeliveryPostalCode(),
                order.getDeliveryCity(),
                order.getDeliveryCountry(),
                order.getContactEmail(),
                order.getItems(),
                LocalDateTime.now(),
                OrderStatus.PENDING_DECISION,
                null
        );
        return repository.save(toSave);
    }

    @Override
    public void acceptOrder(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.accept();
        repository.save(order);
    }

    @Override
    public void rejectOrder(UUID orderId, String reason) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.reject(reason);
        repository.save(order);
    }

    @Override
    public List<Order> getOrdersForRestaurant(UUID restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @Override
    public void markOrderReady(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.markReady();
        repository.save(order);
        String routingKey = "restaurant." + order.getRestaurantId() + ".order.ready.v1";
        rabbitTemplate.convertAndSend("kdg.events", routingKey,
                Map.of("orderId", order.getId(), "restaurantId", order.getRestaurantId()));
    }
}
