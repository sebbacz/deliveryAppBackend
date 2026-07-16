package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.ScheduleDishChangesCmd;
import be.kdg.dishsg.catalog.ports.in.ScheduleDishChangesUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultScheduleDishChangesUseCase implements ScheduleDishChangesUseCase {

    private static final int MAX_LIVE_DISHES = 10;

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultScheduleDishChangesUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void scheduleChanges(ScheduleDishChangesCmd cmd) {
        List<Dish> dishesWithPendingDraft = loadDishPort.findWithPendingDraftByRestaurantId(cmd.restaurantId());
        if (dishesWithPendingDraft.isEmpty()) {
            throw new IllegalStateException("No pending dish drafts to schedule for restaurant: " + cmd.restaurantId());
        }
        dishesWithPendingDraft.forEach(dish -> dish.schedulePublishAt(cmd.scheduledAt()));
        saveDishPort.saveAll(dishesWithPendingDraft);
    }

    // Run every 30 seconds, publishes any drafts whose scheduled time has arrived
    @Scheduled(fixedDelay = 30_000)
    public void applyDueScheduledChanges() {
        List<Dish> due = loadDishPort.findScheduledDraftsDue(LocalDateTime.now());
        if (due.isEmpty()) return;

        due.forEach(dish -> {
            long currentlyLive = loadDishPort.countLiveByRestaurantId(dish.getRestaurantId());
            long thisBatchSize = due.stream()
                    .filter(d -> d.getRestaurantId().equals(dish.getRestaurantId()))
                    .count();
            if (currentlyLive + thisBatchSize <= MAX_LIVE_DISHES) {
                dish.publish();
            } else {
                // Limit would be exceeded — clear the schedule without publishing
                dish.clearSchedule();
            }
        });
        saveDishPort.saveAll(due);
    }
}
