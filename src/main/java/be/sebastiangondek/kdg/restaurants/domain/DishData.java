package be.sebastiangondek.kdg.restaurants.domain;

import java.util.List;

//  dish version (live or draft).
public record DishData(
        String name,
        DishType type,
        List<String> foodTags,
        String description,
        double price,
        String pictureUrl
) {}
