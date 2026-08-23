package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

// Command for marking an accepted order ready for courier pickup; triggers a RabbitMQ ready event.
public record MarkOrderReadyCmd(UUID orderId) {}
