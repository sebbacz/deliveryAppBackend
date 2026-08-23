package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.UpdateDishStockUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Allows an owner to toggle availability without removing a dish; out-of-stock dishes are blocked at checkout.
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
