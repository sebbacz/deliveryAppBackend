package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.GetOwnerDishesUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// Returns all dishes for a restaurant (DRAFT + LIVE + LIVE_WITH_PENDING) for the owner management view.
@Transactional(readOnly = true)
@Service
public class DefaultGetOwnerDishesUseCase implements GetOwnerDishesUseCase {

    private final LoadDishPort loadDishPort;

    public DefaultGetOwnerDishesUseCase(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }

    @Override
    public List<Dish> getOwnerDishes(UUID restaurantId) {
        return loadDishPort.findAllByRestaurantId(restaurantId);
    }
}
