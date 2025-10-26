package be.kdg.dishsg.catalog.adapter;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class DishRepositoryAdapter implements DishRepositoryPort {

    private final Map<String, Dish> database = new HashMap<>();

    @Override
    public Optional<Dish> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Dish save(Dish dish) {
        database.put(dish.getId(), dish);
        return dish;
    }
}