package be.kdg.dishsg.dish.app;

import be.kdg.dishsg.dish.domain.Dish;
import be.kdg.dishsg.dish.repository.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ApplyPendingDishesServiceImpl {
    private static final int MAX_LIVE_DISHES = 10;

    private final DishRepositoryPort dishRepositoryPort;

    public ApplyPendingDishesServiceImpl(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public void applyPendingChanges(UUID restaurantId) {
        List<Dish> drafts = dishRepositoryPort.findAllDraftsByRestaurant(restaurantId);
        long currentlyLive = dishRepositoryPort.countLiveByRestaurant(restaurantId);
        long resultingLive = currentlyLive + drafts.size();

        if (resultingLive > MAX_LIVE_DISHES) {
            throw new IllegalStateException("A restaurant can have at most 10 published dishes.");
        }


        for (Dish dish : drafts) {
            dish.setDraft(false);
        }

        dishRepositoryPort.saveAll(drafts);
    }
}
