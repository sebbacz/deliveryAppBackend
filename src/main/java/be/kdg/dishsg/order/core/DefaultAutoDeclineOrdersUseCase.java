package be.kdg.dishsg.order.core;

import be.kdg.dishsg.order.ports.in.AutoDeclineOrdersUseCase;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
