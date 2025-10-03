package be.kdg.dishsg.catalog.port.in;

import java.util.UUID;

public record UnpublishDishCommand(UUID dishId) {
}
