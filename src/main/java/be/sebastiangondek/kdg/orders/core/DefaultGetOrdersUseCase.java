package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.ports.in.GetOrdersUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// Returns all orders for a restaurant from the CQRS projection table,
@Service
public class DefaultGetOrdersUseCase implements GetOrdersUseCase {

    private final OrderRepositoryPort repository;

    public DefaultGetOrdersUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> getOrdersForRestaurant(UUID restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }
}
