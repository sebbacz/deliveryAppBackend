package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.ports.in.GetBusynessUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultGetBusynessUseCase implements GetBusynessUseCase {

    private final OrderRepositoryPort repository;

    public DefaultGetBusynessUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public int getActiveOrderCount(UUID restaurantId) {
        return repository.countActiveByRestaurantId(restaurantId);
    }
}
