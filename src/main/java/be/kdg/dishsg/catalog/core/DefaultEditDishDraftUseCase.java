package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.EditDishDraftUseCase;
import be.kdg.dishsg.catalog.ports.in.UpdateDishDraftCmd;
import be.kdg.dishsg.catalog.ports.out.LoadDishPort;
import be.kdg.dishsg.catalog.ports.out.SaveDishPort;
import org.springframework.stereotype.Service;

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
