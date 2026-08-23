package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.domain.DishData;
import be.sebastiangondek.kdg.restaurants.ports.in.CreateDishDraftUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.SaveDishDraftCmd;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Creates a new dish in DRAFT stat.
@Service
public class DefaultCreateDishDraftUseCase implements CreateDishDraftUseCase {

    private final SaveDishPort saveDishPort;

    public DefaultCreateDishDraftUseCase(SaveDishPort saveDishPort) {
        this.saveDishPort = saveDishPort;
    }

    @Override
    public Dish createDraft(SaveDishDraftCmd cmd) {
        DishData draft = new DishData(cmd.name(), cmd.type(), cmd.foodTags(), cmd.description(), cmd.price(), cmd.pictureUrl());
        Dish dish = new Dish(UUID.randomUUID(), cmd.restaurantId(), null, draft, true, null);
        return saveDishPort.save(dish);
    }
}
