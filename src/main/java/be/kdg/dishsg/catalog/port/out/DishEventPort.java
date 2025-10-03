package be.kdg.dishsg.catalog.port.out;

import be.kdg.dishsg.catalog.domain.Dish;

public interface DishEventPort {
    void publishDishPublished(Dish dish);
    void publishDishUnpublished(Dish dish);
}
