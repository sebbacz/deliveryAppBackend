package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.UpdateDishStockUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultSetDishStockStatusUseCase implements UpdateDishStockUseCase {

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultSetDishStockStatusUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void markOutOfStock(UUID id) {
        Dish dish = loadDishPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        dish.markOutOfStock();
        saveDishPort.save(dish);
    }

    @Override
    public void markBackInStock(UUID id) {
        Dish dish = loadDishPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        dish.markInStock();
        saveDishPort.save(dish);
    }
}
