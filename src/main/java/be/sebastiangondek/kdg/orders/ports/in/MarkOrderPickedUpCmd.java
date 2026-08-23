package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

// Command sent by the delivery service (via RabbitMQ) when the courier collects the order.
public record MarkOrderPickedUpCmd(UUID orderId) {}
