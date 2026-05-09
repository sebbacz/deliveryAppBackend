package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.UpdateDishStockUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DishStockService implements UpdateDishStockUseCase {

    private final DishRepositoryPort repository;

    public DishStockService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void markOutOfStock(UUID dishId) {
        Dish dish = repository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + dishId));
        dish.markOutOfStock();
        repository.save(dish);
    }

    @Override
    public void markBackInStock(UUID dishId) {
        Dish dish = repository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + dishId));
        dish.markInStock();
        repository.save(dish);
    }
}
