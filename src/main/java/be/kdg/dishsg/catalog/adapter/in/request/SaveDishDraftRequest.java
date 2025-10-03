package be.kdg.dishsg.catalog.adapter.in.request;

import java.math.BigDecimal;

public record SaveDishDraftRequest(String restaurantId, String name, String description, BigDecimal price) {
}
