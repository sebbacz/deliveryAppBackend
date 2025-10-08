package be.kdg.dishsg.restaurant.port.in;

import be.kdg.dishsg.restaurant.domain.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant create(CreateRestaurantCommand command);
}
