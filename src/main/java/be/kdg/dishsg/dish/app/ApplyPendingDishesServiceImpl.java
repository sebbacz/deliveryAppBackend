package be.kdg.dishsg.dish.app;

import be.kdg.dishsg.dish.domain.Dish;
import be.kdg.dishsg.dish.repository.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ApplyPendingDishesServiceImpl {

    private final DishRepositoryPort dishRepositoryPort;

    public ApplyPendingDishesServiceImpl(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public void applyPendingChanges(UUID restaurantId) {
        List<Dish> drafts = dishRepositoryPort.findAllDraftsByRestaurant(restaurantId);


        for (Dish dish : drafts) {
            dish.setDraft(false);
        }

        dishRepositoryPort.saveAll(drafts);
    }
}
