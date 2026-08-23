package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

// Inbound port for update  the pending draft of a dish without affecting the live version.
public interface EditDishDraftUseCase {
    Dish updateDraft(UpdateDishDraftCmd cmd);
}
