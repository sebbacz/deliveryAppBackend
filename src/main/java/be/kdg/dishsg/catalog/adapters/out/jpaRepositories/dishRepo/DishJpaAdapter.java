package be.kdg.dishsg.catalog.adapters.out.jpaRepositories.dishRepo;

import be.kdg.dishsg.catalog.adapters.out.jpaRepositories.dishRepo.entities.DishJpaEntity;
import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.DishData;
import be.kdg.dishsg.catalog.domain.DishType;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DishJpaAdapter implements DishRepositoryPort {

    private final SpringDataDishRepository springRepo;

    public DishJpaAdapter(SpringDataDishRepository springRepo) {
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
                .filter(e -> e.getLiveName() != null || e.getDraftName() != null)
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> findLiveByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantIdAndLiveNameIsNotNull(restaurantId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> findWithPendingDraftByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantIdAndDraftNameIsNotNull(restaurantId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countLiveByRestaurantId(UUID restaurantId) {
        return springRepo.countByRestaurantIdAndLiveNameIsNotNull(restaurantId);
    }

    @Override
    public List<Dish> saveAll(List<Dish> dishes) {
        springRepo.saveAll(dishes.stream().map(this::toEntity).collect(Collectors.toList()));
        return dishes;
    }

    @Override
    public List<Dish> findScheduledDraftsDue(LocalDateTime now) {
        return springRepo.findByDraftNameIsNotNullAndScheduledAtIsNotNullAndScheduledAtLessThanEqual(now).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private DishJpaEntity toEntity(Dish dish) {
        DishData live  = dish.getLive();
        DishData draft = dish.getDraft();
        return new DishJpaEntity(
                dish.getId(),
                dish.getRestaurantId(),
                dish.isInStock(),
                dish.getScheduledAt(),
                live  != null ? live.name()        : null,
                live  != null ? live.type() != null ? live.type().name() : null : null,
                live  != null ? live.foodTags()    : null,
                live  != null ? live.description() : null,
                live  != null ? live.price()       : null,
                live  != null ? live.pictureUrl()  : null,
                draft != null ? draft.name()        : null,
                draft != null ? draft.type() != null ? draft.type().name() : null : null,
                draft != null ? draft.foodTags()    : null,
                draft != null ? draft.description() : null,
                draft != null ? draft.price()       : null,
                draft != null ? draft.pictureUrl()  : null
        );
    }

    private Dish toDomain(DishJpaEntity e) {
        DishData live = e.getLiveName() != null ? new DishData(
                e.getLiveName(),
                e.getLiveType() != null ? DishType.valueOf(e.getLiveType()) : null,
                e.getLiveFoodTags() != null ? e.getLiveFoodTags() : List.of(),
                e.getLiveDescription(),
                e.getLivePrice() != null ? e.getLivePrice() : 0.0,
                e.getLivePictureUrl()
        ) : null;

        DishData draft = e.getDraftName() != null ? new DishData(
                e.getDraftName(),
                e.getDraftType() != null ? DishType.valueOf(e.getDraftType()) : null,
                e.getDraftFoodTags() != null ? e.getDraftFoodTags() : List.of(),
                e.getDraftDescription(),
                e.getDraftPrice() != null ? e.getDraftPrice() : 0.0,
                e.getDraftPictureUrl()
        ) : null;

        return new Dish(e.getId(), e.getRestaurantId(), live, draft, e.isInStock(), e.getScheduledAt());
    }
}
