package be.kdg.dishsg.order.adapters.in.scheduler;

import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// Scheduled job that auto-rejects orders that have been pending for more than 5 minutes.
@Service
public class AutoDeclineOrdersScheduler {

    private final OrderRepositoryPort repository;

    public AutoDeclineOrdersScheduler(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    // US11: auto-decline orders pending for more than 5 minutes
    @Scheduled(fixedDelay = 30_000)
    public void declineExpiredOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        // Load IDs from the projection, then reconstitute each aggregate from
        // the event store so the reject command raises the correct domain event.
        repository.findPendingOrderIdsBefore(cutoff).forEach(orderId ->
                repository.findById(orderId).ifPresent(order -> {
                    order.reject("Auto-declined: no decision made within 5 minutes");
                    repository.save(order);
                })
        );
    }
}
