package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.PublishDishUseCase;
import be.kdg.dishsg.catalog.ports.in.UnpublishDishUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;


@Service
public class DishPublishingService implements PublishDishUseCase, UnpublishDishUseCase {

    private final DishRepositoryPort repository;

    public DishPublishingService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void publishDish(String id) {
        Dish dish = repository.findById(id).orElseThrow();
        dish.publish();
        repository.save(dish);
    }

    @Override
    public void unpublishDish(String id) {
        Dish dish = repository.findById(id).orElseThrow();
        dish.unpublish();
        repository.save(dish);
    }
}
