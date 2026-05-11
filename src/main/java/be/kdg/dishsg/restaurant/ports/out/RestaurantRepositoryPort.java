package be.kdg.dishsg.restaurant.ports.out;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public  interface RestaurantRepositoryPort {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(String id);
    List<Restaurant> findByOwnerId(String ownerId);
    List<Restaurant> findAll();
}
