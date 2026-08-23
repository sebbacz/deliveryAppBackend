package be.sebastiangondek.kdg.restaurants.ports.out;

import be.sebastiangondek.kdg.restaurants.domain.Dish;

import java.util.List;

// Out-port for persisting one or many dishes after domain change
public interface SaveDishPort {
    Dish save(Dish dish);
    List<Dish> saveAll(List<Dish> dishes);
}
