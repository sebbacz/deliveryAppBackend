package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.ApplyPendingChangesUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

        // Only DRAFT-only dishes (live==null) increase the live count; LIVE_WITH_PENDING just update
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
