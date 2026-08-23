package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.ScheduleDishChangesCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.ScheduleDishChangesUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// timestamp on pending drafts and  a 30-second scheduler
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

    // Run every 30 seconds, publishes any drafts whose scheduled time
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
                // Limit would be exceeded
                dish.clearSchedule();
            }
        });
        saveDishPort.saveAll(due);
    }
}
