package be.kdg.dishsg.catalog.domain;

import java.util.List;

public record DishData(
        String name,
        DishType type,
        List<String> foodTags,
        String description,
        double price,
        String pictureUrl
) {}
