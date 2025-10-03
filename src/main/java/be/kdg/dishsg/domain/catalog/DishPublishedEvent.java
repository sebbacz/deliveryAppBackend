package be.kdg.dishsg.domain.catalog;

import java.util.UUID;

public record DishPublishedEvent(UUID dishId, UUID restaurantId, String name) { }