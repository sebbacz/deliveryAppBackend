package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetOwnerDishesUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetOwnerDishesService implements GetOwnerDishesUseCase {

    private final DishRepositoryPort repository;

    public GetOwnerDishesService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Dish> getOwnerDishes(UUID restaurantId) {
        return repository.findAllByRestaurantId(restaurantId);
    }
}
