package be.kdg.dishsg.catalog.adapter.out.persistence;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.DishType;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DishPersistenceAdapter implements DishRepositoryPort {

    private final SpringDataDishRepository springRepo;

    public DishPersistenceAdapter(SpringDataDishRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Dish save(Dish dish) {
        springRepo.save(toEntity(dish));
        return dish;
    }

    @Override
    public Optional<Dish> findById(UUID id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Dish> findAllByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantId(restaurantId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> findAllDraftsByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantIdAndState(restaurantId, Dish.DishState.DRAFT.name()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> findLiveByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantIdAndState(restaurantId, Dish.DishState.LIVE.name()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countLiveByRestaurantId(UUID restaurantId) {
        return springRepo.countByRestaurantIdAndState(restaurantId, Dish.DishState.LIVE.name());
    }

    @Override
    public List<Dish> saveAll(List<Dish> dishes) {
        springRepo.saveAll(dishes.stream().map(this::toEntity).collect(Collectors.toList()));
        return dishes;
    }

    @Override
    public List<Dish> findScheduledDraftsDue(LocalDateTime now) {
        return springRepo.findByStateAndScheduledAtLessThanEqual(Dish.DishState.DRAFT.name(), now)
                .stream().map(this::toDomain).collect(Collectors.toList());
    }

    private DishJpaEntity toEntity(Dish dish) {
        return new DishJpaEntity(
                dish.getId(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getType() != null ? dish.getType().name() : null,
                dish.getFoodTags(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                dish.isInStock(),
                dish.getState().name(),
                dish.getScheduledAt()
        );
    }

    private Dish toDomain(DishJpaEntity entity) {
        return new Dish(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getName(),
                entity.getType() != null ? DishType.valueOf(entity.getType()) : null,
                entity.getFoodTags(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureUrl(),
                entity.isInStock(),
                Dish.DishState.valueOf(entity.getState()),
                entity.getScheduledAt()
        );
    }
}
