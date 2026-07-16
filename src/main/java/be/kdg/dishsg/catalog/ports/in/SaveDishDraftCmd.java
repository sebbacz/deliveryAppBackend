package be.kdg.dishsg.catalog.ports.in;

import be.kdg.dishsg.catalog.domain.DishType;

import java.util.List;
import java.util.UUID;

public record SaveDishDraftCmd(
        UUID restaurantId,
        String name,
        DishType type,
        List<String> foodTags,
        String description,
        double price,
        String pictureUrl
) {}
