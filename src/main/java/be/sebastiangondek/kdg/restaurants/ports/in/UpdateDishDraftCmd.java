package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.DishType;

import java.util.List;
import java.util.UUID;

// Command for overwriting the pending draft of an existing dish; used by DefaultEditDishDraftUseCase.
public record UpdateDishDraftCmd(
        UUID dishId,
        String name,
        DishType type,
        List<String> foodTags,
        String description,
        double price,
        String pictureUrl
) {}
