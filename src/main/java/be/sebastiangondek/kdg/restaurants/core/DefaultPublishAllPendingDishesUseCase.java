package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.ApplyPendingChangesUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// Publishes all pending drafts at once; enforces the 10 live dish cap .
@Transactional
@Service
public class DefaultPublishAllPendingDishesUseCase implements ApplyPendingChangesUseCase {

    private static final int MAX_LIVE_DISHES = 10;

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultPublishAllPendingDishesUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void applyPendingChanges(UUID restaurantId) {
        List<Dish> dishesWithPendingDraft = loadDishPort.findWithPendingDraftByRestaurantId(restaurantId);
        long currentlyLive = loadDishPort.countLiveByRestaurantId(restaurantId);

        // Only DRAFT-only dishes  increase the live count; LIVE_WITH_PENDING just update
        long newDishCount = dishesWithPendingDraft.stream()
                .filter(d -> d.getLive() == null)
                .count();

        if (currentlyLive + newDishCount > MAX_LIVE_DISHES) {
            throw new IllegalStateException("Publishing all pending dishes would exceed the 10-dish live limit.");
        }

        dishesWithPendingDraft.forEach(Dish::publish);
        saveDishPort.saveAll(dishesWithPendingDraft);
    }
}
