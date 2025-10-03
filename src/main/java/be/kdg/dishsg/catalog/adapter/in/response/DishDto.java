package be.kdg.dishsg.catalog.adapter.in.response;



import be.kdg.dishsg.catalog.domain.Dish;

import java.math.BigDecimal;
import java.util.UUID;

public record DishDto(UUID id, UUID restaurantId, String name, String description, BigDecimal price, String state, boolean inStock) {
    public static DishDto from(Dish dish) {
        return new DishDto(dish.getId(), dish.getRestaurantId(), dish.getName(),
                dish.getDescription(), dish.getPrice(), dish.getState().name(), dish.isInStock());
    }
}