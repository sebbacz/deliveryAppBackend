package be.kdg.dishsg.order.adapters.in.scheduler;

import be.kdg.dishsg.order.ports.in.AutoDeclineOrdersUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoDeclineOrdersScheduler {

    private final AutoDeclineOrdersUseCase autoDeclineOrdersUseCase;

    public AutoDeclineOrdersScheduler(AutoDeclineOrdersUseCase autoDeclineOrdersUseCase) {
        this.autoDeclineOrdersUseCase = autoDeclineOrdersUseCase;
    }

    @Scheduled(fixedDelay = 30_000)
    public void declineExpiredOrders() {
        autoDeclineOrdersUseCase.declineExpiredOrders();
    }
}
