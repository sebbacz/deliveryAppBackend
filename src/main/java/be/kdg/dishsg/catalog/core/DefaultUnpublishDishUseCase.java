package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.UnpublishDishUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
