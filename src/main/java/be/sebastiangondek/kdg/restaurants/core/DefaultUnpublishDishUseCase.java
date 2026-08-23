package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.UnpublishDishUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Removes a dish from the live menu; copies live to draft so the data is nto lost
@Service
public class DefaultUnpublishDishUseCase implements UnpublishDishUseCase {

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultUnpublishDishUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void unpublishDish(UUID id) {
        Dish dish = loadDishPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        dish.unpublish();
        saveDishPort.save(dish);
    }
}
