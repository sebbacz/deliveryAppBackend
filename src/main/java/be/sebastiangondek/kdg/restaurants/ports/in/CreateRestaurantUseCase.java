package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Restaurant;

// Inbound port for registering a new restaurant
public interface CreateRestaurantUseCase {
    Restaurant createRestaurant(CreateRestaurantCmd cmd);
}
