package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.DishData;
import be.kdg.dishsg.catalog.ports.in.CreateDishDraftUseCase;
import be.kdg.dishsg.catalog.ports.in.SaveDishDraftCmd;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
