package be.sebastiangondek.kdg.restaurants.ports.out;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

// Out-port for persisting a restaurant after domain change
public interface SaveRestaurantPort {
    Restaurant save(Restaurant restaurant);
}
