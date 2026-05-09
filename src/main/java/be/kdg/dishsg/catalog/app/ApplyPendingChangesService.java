package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.ApplyPendingChangesUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApplyPendingChangesService implements ApplyPendingChangesUseCase {

    private static final int MAX_LIVE_DISHES = 10;

    private final DishRepositoryPort repository;

    public ApplyPendingChangesService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void applyPendingChanges(UUID restaurantId) {
        List<Dish> drafts = repository.findAllDraftsByRestaurantId(restaurantId);
        long currentlyLive = repository.countLiveByRestaurantId(restaurantId);

        if (currentlyLive + drafts.size() > MAX_LIVE_DISHES) {
            throw new IllegalStateException(
                    "Publishing all pending dishes would exceed the 10-dish limit.");
        }

        drafts.forEach(Dish::publish);
        repository.saveAll(drafts);
    }
}
