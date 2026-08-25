package be.sebastiangondek.kdg.orders.core;

import be.sebastiangondek.kdg.orders.ports.in.AutoDeclineOrdersUseCase;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

// Auto-rejects orders that go undecided for more than 5 minutes; run every 30 s by AutoDeclineOrdersScheduler.
@Transactional
@Service
public class DefaultAutoDeclineOrdersUseCase implements AutoDeclineOrdersUseCase {

    private final OrderRepositoryPort repository;

    public DefaultAutoDeclineOrdersUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void declineExpiredOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        repository.findPendingOrderIdsBefore(cutoff).forEach(orderId ->
                repository.findById(orderId).ifPresent(order -> {
                    order.reject("Auto-declined: no decision made within 5 minutes");
                    repository.save(order);
                })
        );
    }
}
