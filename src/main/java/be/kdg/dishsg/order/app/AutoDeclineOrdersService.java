package be.kdg.dishsg.order.app;

import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AutoDeclineOrdersService {

    private final OrderRepositoryPort repository;

    public AutoDeclineOrdersService(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    // US11: auto-decline orders pending for more than 5 minutes
    @Scheduled(fixedDelay = 30_000)
    public void declineExpiredOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        repository.findPendingOrdersBefore(cutoff).forEach(order -> {
            order.reject("Auto-declined: no decision made within 5 minutes");
            repository.save(order);
        });
    }
}
