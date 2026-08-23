package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

// Command for rejecting an order with a mandatory reason surfaced to the customer on the tracking page.
public record RejectOrderCmd(UUID orderId, String reason) {}
