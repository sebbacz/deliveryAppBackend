package be.sebastiangondek.kdg.orders.adapters.in.scheduler;

import be.sebastiangondek.kdg.orders.ports.in.AutoDeclineOrdersUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

// Scheduler adapter that triggers DefaultAutoDeclineOrdersUseCase every 30 s to auto-reject pending orders.
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
