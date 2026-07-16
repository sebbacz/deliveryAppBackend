package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

// In-port for toggling a dish's stock availability.
public interface UpdateDishStockUseCase {
    void markOutOfStock(UUID dishId);
    void markBackInStock(UUID dishId);
}
