package be.kdg.dishsg.order.domain;

// Lifecycle states an order passes through from placement to final delivery.
public enum OrderStatus {
    PENDING_DECISION,
    ACCEPTED,
    REJECTED,
    READY_FOR_PICKUP,
    PICKED_UP,
    DELIVERED
}
