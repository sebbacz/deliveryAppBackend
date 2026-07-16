package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.ports.in.GetOrdersUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
