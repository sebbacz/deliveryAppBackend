package be.sebastiangondek.kdg.restaurants.ports.out;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

import java.util.List;
import java.util.Optional;

// Out-port for reading restaurants by ID, owner ID, or listing all.
public interface LoadRestaurantPort {
    Optional<Restaurant> findById(String id);
    List<Restaurant> findByOwnerId(String ownerId);
    List<Restaurant> findAll();
}
