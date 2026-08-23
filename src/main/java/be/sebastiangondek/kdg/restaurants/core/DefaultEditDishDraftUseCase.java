package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Dish;
import be.sebastiangondek.kdg.restaurants.ports.in.EditDishDraftUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.UpdateDishDraftCmd;
import be.sebastiangondek.kdg.restaurants.ports.out.LoadDishPort;
import be.sebastiangondek.kdg.restaurants.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

// Overwrites the draft data on an existing dish.
@Service
public class DefaultEditDishDraftUseCase implements EditDishDraftUseCase {

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public DefaultEditDishDraftUseCase(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public Dish updateDraft(UpdateDishDraftCmd cmd) {
        Dish dish = loadDishPort.findById(cmd.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + cmd.dishId()));
        dish.saveDraft(cmd.name(), cmd.type(), cmd.foodTags(), cmd.description(), cmd.price(), cmd.pictureUrl());
        return saveDishPort.save(dish);
    }
}
