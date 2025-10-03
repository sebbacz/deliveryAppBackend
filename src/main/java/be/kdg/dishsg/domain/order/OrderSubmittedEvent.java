package be.kdg.dishsg.domain.order;

import java.util.UUID;

public record OrderSubmittedEvent(UUID orderId, UUID restaurantId) { }