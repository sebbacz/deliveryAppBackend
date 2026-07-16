package be.kdg.dishsg.order.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.UUID;

// Sealed interface for all Order domain events; Jackson uses the "type" field for polymorphic deserialization.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderCreatedEvent.class,              name = "OrderCreated"),
        @JsonSubTypes.Type(value = OrderAcceptedEvent.class,             name = "OrderAccepted"),
        @JsonSubTypes.Type(value = OrderRejectedEvent.class,             name = "OrderRejected"),
        @JsonSubTypes.Type(value = OrderMarkedReadyForPickupEvent.class, name = "OrderMarkedReady"),
        @JsonSubTypes.Type(value = OrderPickedUpEvent.class,             name = "OrderPickedUp"),
        @JsonSubTypes.Type(value = OrderDeliveredEvent.class,            name = "OrderDelivered"),
        @JsonSubTypes.Type(value = OrderCourierLocationUpdatedEvent.class, name = "OrderCourierLocationUpdated")
})
public sealed interface OrderEvent
        permits OrderCreatedEvent, OrderAcceptedEvent, OrderRejectedEvent,
                OrderMarkedReadyForPickupEvent, OrderPickedUpEvent,
                OrderDeliveredEvent, OrderCourierLocationUpdatedEvent {

    UUID orderId();
    LocalDateTime occurredAt();
}
