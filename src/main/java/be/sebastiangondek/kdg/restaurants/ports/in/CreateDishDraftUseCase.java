package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

// Inbound port for creating a new dish in DRAFT state
public interface CreateDishDraftUseCase {
    Dish createDraft(SaveDishDraftCmd cmd);
}
