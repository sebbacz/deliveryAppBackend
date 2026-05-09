package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.PublishDishUseCase;
import be.kdg.dishsg.catalog.ports.in.UnpublishDishUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DishPublishingService implements PublishDishUseCase, UnpublishDishUseCase {

    private final DishRepositoryPort repository;

    public DishPublishingService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void publishDish(UUID id) {
        Dish dish = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        dish.publish();
        repository.save(dish);
    }

    @Override
    public void unpublishDish(UUID id) {
        Dish dish = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        dish.unpublish();
        repository.save(dish);
    }
}
