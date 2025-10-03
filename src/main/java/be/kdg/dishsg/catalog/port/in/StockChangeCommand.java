package be.kdg.dishsg.catalog.port.in;

import java.util.UUID;

public record StockChangeCommand(UUID dishId, boolean inStock) {
}
