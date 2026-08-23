package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.DishType;

import java.util.List;
import java.util.UUID;

// Command for creating a new dish in DRAFT state; used by DefaultCreateDishDraftUseCase.
public record SaveDishDraftCmd(
        UUID restaurantId,
        String name,
        DishType type,
        List<String> foodTags,
        String description,
        double price,
        String pictureUrl
) {}
