package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.GetPublishedDishesUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

// Returns only LIVE dishes; used by the customer-facing menu and checkout stock validation.
@Service
public class DefaultGetPublishedDishesUseCase implements GetPublishedDishesUseCase {

    private final LoadDishPort loadDishPort;

    public DefaultGetPublishedDishesUseCase(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }

    @Override
    public List<Dish> getPublishedDishes(UUID restaurantId) {
        return loadDishPort.findLiveByRestaurantId(restaurantId);
    }
}
