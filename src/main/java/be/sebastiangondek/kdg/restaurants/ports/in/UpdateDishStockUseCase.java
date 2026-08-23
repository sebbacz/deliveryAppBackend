package be.sebastiangondek.kdg.restaurants.ports.in;

import java.util.UUID;

// In-port for toggling a dishs stock availability.
public interface UpdateDishStockUseCase {
    void markOutOfStock(UUID dishId);
    void markBackInStock(UUID dishId);
}
