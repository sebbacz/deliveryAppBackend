package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetPublishedDishesUseCase;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetPublishedDishesService implements GetPublishedDishesUseCase {

    private final DishRepositoryPort repository;

    public GetPublishedDishesService(DishRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Dish> getPublishedDishes(UUID restaurantId) {
        return repository.findLiveByRestaurantId(restaurantId);
    }
}
