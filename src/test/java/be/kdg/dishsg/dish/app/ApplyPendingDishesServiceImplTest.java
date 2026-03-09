package be.kdg.dishsg.dish.app;

import be.kdg.dishsg.dish.domain.Dish;
import be.kdg.dishsg.dish.repository.DishRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplyPendingDishesServiceImplTest {

    @Test
    void appliesDraftsWhenRestaurantWouldStayWithinLimit() {
        InMemoryDishRepository repository = new InMemoryDishRepository(8);
        UUID restaurantId = UUID.randomUUID();
        Dish draft = new Dish("Starter", "desc", 12.0, restaurantId, true, true);
        repository.drafts.add(draft);

        ApplyPendingDishesServiceImpl service = new ApplyPendingDishesServiceImpl(repository);

        service.applyPendingChanges(restaurantId);

        assertFalse(draft.isDraft());
    }

    @Test
    void throwsWhenApplyingDraftsWouldExceedLimit() {
        InMemoryDishRepository repository = new InMemoryDishRepository(10);
        UUID restaurantId = UUID.randomUUID();
        Dish draft = new Dish("Main", "desc", 22.0, restaurantId, true, true);
        repository.drafts.add(draft);

        ApplyPendingDishesServiceImpl service = new ApplyPendingDishesServiceImpl(repository);

        assertThrows(IllegalStateException.class, () -> service.applyPendingChanges(restaurantId));
    }

    private static final class InMemoryDishRepository implements DishRepositoryPort {
        private final long liveCount;
        private final List<Dish> drafts = new ArrayList<>();

        private InMemoryDishRepository(long liveCount) {
            this.liveCount = liveCount;
        }

        @Override
        public Optional<Dish> findById(UUID id) {
            return Optional.empty();
        }

        @Override
        public void save(Dish dish) {
        }

        @Override
        public List<Dish> findAllDraftsByRestaurant(UUID restaurantId) {
            return drafts;
        }

        @Override
        public long countLiveByRestaurant(UUID restaurantId) {
            return liveCount;
        }

        @Override
        public void saveAll(List<Dish> dishes) {
        }
    }
}
