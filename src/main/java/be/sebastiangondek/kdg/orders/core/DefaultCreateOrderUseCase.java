package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.OrderItem;
import be.sebastiangondek.kdg.orders.ports.in.CreateOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.CreateOrderUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import be.sebastiangondek.kdg.orders.domain.exception.RestaurantClosedException;
import be.sebastiangondek.kdg.orders.ports.out.RestaurantStatusPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

// Validates restaurant is open before placing;
@Transactional
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
        Order order = new Order(
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
