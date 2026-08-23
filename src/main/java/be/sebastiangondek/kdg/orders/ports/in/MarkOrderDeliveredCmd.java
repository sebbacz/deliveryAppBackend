package be.sebastiangondek.kdg.orders.ports.in;

import java.util.UUID;

//  command sent by the delivery service when the order is handed to the customer.
public record MarkOrderDeliveredCmd(UUID orderId) {}
