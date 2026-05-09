package be.kdg.dishsg.catalog.ports.in;

import java.util.UUID;

public interface UpdateDishStockUseCase {
    void markOutOfStock(UUID dishId);
    void markBackInStock(UUID dishId);
}
