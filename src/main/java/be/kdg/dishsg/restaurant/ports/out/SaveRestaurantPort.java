package be.kdg.dishsg.restaurant.ports.out;

import be.kdg.dishsg.restaurant.domain.model.Restaurant;

public interface SaveRestaurantPort {
    Restaurant save(Restaurant restaurant);
}
