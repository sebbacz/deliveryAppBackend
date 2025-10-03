package be.kdg.dishsg.catalog.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record SaveDishDraftCommand(UUID restaurantId, String name, String description, BigDecimal price) {
}
