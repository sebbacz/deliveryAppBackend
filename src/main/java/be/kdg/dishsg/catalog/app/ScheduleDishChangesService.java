package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.ScheduleDishChangesUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleDishChangesService implements ScheduleDishChangesUseCase {

    private static final int MAX_LIVE_DISHES = 10;

    private final DishRepositoryPort repository;

    public ScheduleDishChangesService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void scheduleChanges(UUID restaurantId, LocalDateTime scheduledAt) {
        if (scheduledAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scheduled time must be in the future");
        }
        List<Dish> drafts = repository.findAllDraftsByRestaurantId(restaurantId);
        if (drafts.isEmpty()) {
            throw new IllegalStateException("No pending changes to schedule");
        }
        drafts.forEach(d -> d.schedulePublishAt(scheduledAt));
        repository.saveAll(drafts);
    }

    // Runs every 30 seconds, publishes any drafts whose scheduled time has arrived
    @Scheduled(fixedDelay = 30_000)
    public void applyDueScheduledChanges() {
        List<Dish> due = repository.findScheduledDraftsDue(LocalDateTime.now());
        if (due.isEmpty()) return;

        due.forEach(dish -> {
            long currentlyLive = repository.countLiveByRestaurantId(dish.getRestaurantId());
            long thisBatchSize = due.stream()
                    .filter(d -> d.getRestaurantId().equals(dish.getRestaurantId()))
                    .count();
            if (currentlyLive + thisBatchSize <= MAX_LIVE_DISHES) {
                dish.publish();
                dish.clearSchedule();
            } else {
                // Limit would be exceeded — clear the schedule without publishing
                dish.clearSchedule();
            }
        });
        repository.saveAll(due);
    }
}
