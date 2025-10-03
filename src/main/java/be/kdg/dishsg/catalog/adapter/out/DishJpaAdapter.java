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
        return repo.save(DishJpaEntity.from(dish)).toDomain();
    }

    @Override
    public Optional<Dish> findById(UUID dishId) {
        return repo.findById(dishId).map(DishJpaEntity::toDomain);
    }

    @Override
    public List<Dish> findByRestaurantId(UUID restaurantId) {
        return repo.findByRestaurantId(restaurantId).stream().map(DishJpaEntity::toDomain).toList();
    }

    @Override
    public long countPublishedAndInStock(UUID restaurantId) {
        return repo.countPublishedAndInStock(restaurantId);
    }
}