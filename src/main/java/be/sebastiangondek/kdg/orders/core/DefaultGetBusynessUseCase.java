package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.ports.in.GetBusynessUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// Returns the count of active (PENDING_DECISION + ACCEPTED) orders; used by the frontend estimated delivery times.
@Transactional(readOnly = true)
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
