package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.PublishDishUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// Publishes a single dish draft; enforces the 10 live cap only for new dishes (not updates to existing live ones).
@Transactional
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
