package be.kdg.dishsg.catalog.ports.in;

import be.kdg.dishsg.catalog.domain.Dish;

public interface SaveDishDraftUseCase {

    Dish saveDraft(Dish dish);
}
