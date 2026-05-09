package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.SaveDishDraftUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DishDraftService implements SaveDishDraftUseCase {

    private final DishRepositoryPort repository;

    public DishDraftService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Dish saveDraft(Dish dish) {
        if (dish.getId() != null) {
            Dish existing = repository.findById(dish.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + dish.getId()));
            existing.updateDraft(dish.getName(), dish.getType(), dish.getFoodTags(),
                    dish.getDescription(), dish.getPrice(), dish.getPictureUrl());
            return repository.save(existing);
        }
        Dish newDraft = new Dish(
                UUID.randomUUID(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getType(),
                dish.getFoodTags(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                true,
                Dish.DishState.DRAFT
        );
        return repository.save(newDraft);
    }
}
