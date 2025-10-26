package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.SaveDishDraftUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DishDraftService  implements SaveDishDraftUseCase {

    private final DishRepositoryPort repository;

    public DishDraftService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Dish saveDraft(Dish dish) {
        Dish draft = new Dish(
                dish.getId() != null ? dish.getId() : UUID.randomUUID().toString(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                Dish.DishState.DRAFT
        );
        return repository.save(draft);
    }
}
