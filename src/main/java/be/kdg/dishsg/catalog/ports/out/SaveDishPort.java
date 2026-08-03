package be.kdg.dishsg.catalog.ports.out;

import be.kdg.dishsg.catalog.domain.Dish;

import java.util.List;

public interface SaveDishPort {
    Dish save(Dish dish);
    List<Dish> saveAll(List<Dish> dishes);
}
