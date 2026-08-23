package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.ports.in.GetBusynessUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Returns the count of active (PENDING_DECISION + ACCEPTED) orders; used by the frontend estimated delivery times.
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
