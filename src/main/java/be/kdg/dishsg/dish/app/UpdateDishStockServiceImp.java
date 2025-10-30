package be.kdg.dishsg.dish.app;


import be.kdg.dishsg.dish.domain.Dish;
import be.kdg.dishsg.dish.repository.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateDishStockServiceImp {

    private final DishRepositoryPort dishRepository;



    public UpdateDishStockServiceImp(DishRepositoryPort dishRepository) {
        this.dishRepository = dishRepository;
    }

    public void markOutOfStock(UUID dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));
        dish.setInStock(false);
        dishRepository.save(dish);
    }

    public void markBackInStock(UUID dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));
        dish.setInStock(true);
        dishRepository.save(dish);
    }
}
