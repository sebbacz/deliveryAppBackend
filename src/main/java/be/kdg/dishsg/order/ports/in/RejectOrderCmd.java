package be.kdg.dishsg.order.ports.in;

import java.util.UUID;

public record RejectOrderCmd(UUID orderId, String reason) {}
