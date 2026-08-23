package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

//  owner to accept a pending order; triggers a RabbitMQ event for the delivery service.
public record AcceptOrderCmd(UUID orderId) {}
