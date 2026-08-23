package be.sebastiangondek.kdg.orders.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

//   base for all Order domain events
public sealed interface OrderEvent
        permits OrderPlacedEvent, OrderAcceptedEvent, OrderRejectedEvent,
                OrderReadyEvent, OrderPickedUpEvent, OrderDeliveredEvent,
                CourierLocationUpdatedEvent {

    UUID orderId();
    LocalDateTime occurredAt();
}
