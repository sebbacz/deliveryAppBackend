package be.kdg.dishsg.catalog.adapter.out;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.port.out.DishPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DishJpaAdapter implements DishPersistencePort {
        private final DishJpaRepository repo;

    public DishJpaAdapter(DishJpaRepository repo) {
        this.repo = repo;
    }


    @Override
    public Dish save(Dish dish) {
        return null;
    }

    @Override
    public Optional<Dish> findById(UUID dishId) {
        return Optional.empty();
    }

    @Override
    public List<Dish> findByRestaurantId(UUID restaurantId) {
        return null;
    }

    @Override
    public long countPublishedAndInStock(UUID restaurantId) {
        return 0;
    }
}