package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderItem;
import be.kdg.dishsg.order.ports.in.CreateOrderCmd;
import be.kdg.dishsg.order.ports.in.CreateOrderUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import be.kdg.dishsg.order.domain.exception.RestaurantClosedException;
import be.kdg.dishsg.order.ports.out.RestaurantStatusPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultCreateOrderUseCase implements CreateOrderUseCase {

    private final OrderRepositoryPort repository;
    private final RestaurantStatusPort restaurantStatusPort;

    public DefaultCreateOrderUseCase(OrderRepositoryPort repository, RestaurantStatusPort restaurantStatusPort) {
        this.repository = repository;
        this.restaurantStatusPort = restaurantStatusPort;
    }

    @Override
    public Order createOrder(CreateOrderCmd cmd) {
        if (!restaurantStatusPort.isRestaurantOpen(cmd.restaurantId())) {
            throw new RestaurantClosedException(cmd.restaurantId());
        }
        List<OrderItem> items = cmd.items().stream()
                .map(i -> new OrderItem(UUID.randomUUID(), i.dishId(), i.dishName(), i.price(), i.quantity()))
                .collect(Collectors.toList());
        Order order = Order.create(
                UUID.randomUUID(),
                cmd.restaurantId(),
                cmd.customerName(),
                cmd.deliveryStreet(),
                cmd.deliveryNumber(),
                cmd.deliveryPostalCode(),
                cmd.deliveryCity(),
                cmd.deliveryCountry(),
                cmd.contactEmail(),
                items,
                LocalDateTime.now()
        );
        return repository.save(order);
    }
}
