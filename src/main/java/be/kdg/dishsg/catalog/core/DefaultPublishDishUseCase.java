package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.PublishDishUseCase;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultPublishDishUseCase implements PublishDishUseCase {

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultPublishDishUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void publishDish(UUID id) {
        Dish dish = loadDishPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        long liveCount = loadDishPort.countLiveByRestaurantId(dish.getRestaurantId());
        if (dish.getLive() == null && liveCount >= 10) {
            throw new IllegalStateException("A restaurant cannot have more than 10 live dishes at a time");
        }
        dish.publish();
        saveDishPort.save(dish);
    }
}
