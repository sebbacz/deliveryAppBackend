package be.kdg.dishsg.catalog.app;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplyPendingChangesServiceTest {

    @Test
    void appliesDraftsWhenRestaurantWouldStayWithinLimit() {
        UUID restaurantId = UUID.randomUUID();
        Dish draft = new Dish(UUID.randomUUID(), restaurantId, "Starter", null, null,
                "desc", 12.0, null, true, Dish.DishState.DRAFT);

        InMemoryDishRepository repository = new InMemoryDishRepository(8, List.of(draft));
        ApplyPendingChangesService service = new ApplyPendingChangesService(repository);

        service.applyPendingChanges(restaurantId);

        assertEquals(Dish.DishState.LIVE, draft.getState());
    }

    @Test
    void throwsWhenApplyingDraftsWouldExceedLimit() {
        UUID restaurantId = UUID.randomUUID();
        Dish draft = new Dish(UUID.randomUUID(), restaurantId, "Main", null, null,
                "desc", 22.0, null, true, Dish.DishState.DRAFT);

        InMemoryDishRepository repository = new InMemoryDishRepository(10, List.of(draft));
        ApplyPendingChangesService service = new ApplyPendingChangesService(repository);

        assertThrows(IllegalStateException.class, () -> service.applyPendingChanges(restaurantId));
    }

    private static final class InMemoryDishRepository implements DishRepositoryPort {
        private final long liveCount;
        private final List<Dish> drafts;

        private InMemoryDishRepository(long liveCount, List<Dish> drafts) {
            this.liveCount = liveCount;
            this.drafts = new ArrayList<>(drafts);
        }

        @Override
        public Dish save(Dish dish) { return dish; }

        @Override
        public Optional<Dish> findById(UUID id) { return Optional.empty(); }

        @Override
        public List<Dish> findAllByRestaurantId(UUID restaurantId) { return List.of(); }

        @Override
        public List<Dish> findAllDraftsByRestaurantId(UUID restaurantId) { return drafts; }

        @Override
        public long countLiveByRestaurantId(UUID restaurantId) { return liveCount; }

        @Override
        public List<Dish> saveAll(List<Dish> dishes) { return dishes; }
    }
}
