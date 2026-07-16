package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetOwnerDishesUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
