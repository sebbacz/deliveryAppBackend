package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetPublishedDishesUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
