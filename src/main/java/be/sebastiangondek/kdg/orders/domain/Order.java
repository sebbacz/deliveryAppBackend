package be.sebastiangondek.kdg.orders.domain;

import be.sebastiangondek.kdg.orders.domain.events.CourierLocationUpdatedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderAcceptedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderDeliveredEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderPickedUpEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderPlacedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderReadyEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderRejectedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// Event-sourced aggregate: all state changes flow through raiseEvent → applyEvent;
public class Order {

    private UUID id;
    private UUID restaurantId;
    private String customerName;
    private String deliveryStreet;
    private String deliveryNumber;
    private String deliveryPostalCode;
    private String deliveryCity;
    private String deliveryCountry;
    private String contactEmail;
    private List<OrderItem> items;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String rejectionReason;
    private Double courierLatitude;
    private Double courierLongitude;

    private final List<OrderEvent> uncommittedEvents = new ArrayList<>();
    private long sequenceNumber = 0;

    private Order() {}

    // Used by use cases to place a new order — raises OrderPlacedEvent internally
    public Order(UUID id, UUID restaurantId, String customerName,
                 String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                 String deliveryCity, String deliveryCountry, String contactEmail,
                 List<OrderItem> items, LocalDateTime createdAt) {
        raiseEvent(new OrderPlacedEvent(id, restaurantId, customerName,
                deliveryStreet, deliveryNumber, deliveryPostalCode,
                deliveryCity, deliveryCountry, contactEmail, items, createdAt));
    }

    //  from a full event log (no snapshot available)
    public static Order reconstitute(List<OrderEvent> events) {
        Order order = new Order();
        events.forEach(order::applyAndIncrement);
        return order;
    }

    //  from the latest snapshot plus any events that occurred after it
    public static Order fromSnapshot(OrderSnapshot snapshot, List<OrderEvent> subsequentEvents) {
        Order order = fromProjection(
                snapshot.id(), snapshot.restaurantId(), snapshot.customerName(),
                snapshot.deliveryStreet(), snapshot.deliveryNumber(), snapshot.deliveryPostalCode(),
                snapshot.deliveryCity(), snapshot.deliveryCountry(), snapshot.contactEmail(),
                snapshot.items(), snapshot.createdAt(), snapshot.status(),
                snapshot.rejectionReason(), snapshot.courierLatitude(), snapshot.courierLongitude());
        order.sequenceNumber = snapshot.sequenceNumber();
        subsequentEvents.forEach(order::applyAndIncrement);
        return order;
    }

    // Restore state directly from the CQRS projection — no event replay needed for list queries.
    public static Order fromProjection(UUID id, UUID restaurantId, String customerName,
            String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
            String deliveryCity, String deliveryCountry, String contactEmail,
            List<OrderItem> items, LocalDateTime createdAt, OrderStatus status,
            String rejectionReason, Double courierLatitude, Double courierLongitude) {
        Order order = new Order();
        order.id               = id;
        order.restaurantId     = restaurantId;
        order.customerName     = customerName;
        order.deliveryStreet   = deliveryStreet;
        order.deliveryNumber   = deliveryNumber;
        order.deliveryPostalCode = deliveryPostalCode;
        order.deliveryCity     = deliveryCity;
        order.deliveryCountry  = deliveryCountry;
        order.contactEmail     = contactEmail;
        order.items            = new ArrayList<>(items);
        order.createdAt        = createdAt;
        order.status           = status;
        order.rejectionReason  = rejectionReason;
        order.courierLatitude  = courierLatitude;
        order.courierLongitude = courierLongitude;
        return order;
    }

    // command methods

    public void accept() {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be accepted");
        }
        raiseEvent(new OrderAcceptedEvent(this.id, LocalDateTime.now()));
    }

    public void reject(String reason) {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be rejected");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }
        raiseEvent(new OrderRejectedEvent(this.id, reason, LocalDateTime.now()));
    }

    public void markReady() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("Only accepted orders can be marked ready for pickup");
        }
        raiseEvent(new OrderReadyEvent(this.id, LocalDateTime.now()));
    }

    public void markPickedUp() {
        if (status != OrderStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Only orders ready for pickup can be marked as picked up");
        }
        raiseEvent(new OrderPickedUpEvent(this.id, LocalDateTime.now()));
    }

    public void markDelivered() {
        if (status != OrderStatus.PICKED_UP) {
            throw new IllegalStateException("Only picked up orders can be marked as delivered");
        }
        raiseEvent(new OrderDeliveredEvent(this.id, LocalDateTime.now()));
    }

    public void updateCourierLocation(double latitude, double longitude) {
        raiseEvent(new CourierLocationUpdatedEvent(this.id, latitude, longitude, LocalDateTime.now()));
    }

    //  event sourcing intenrals
    private void raiseEvent(OrderEvent event) {
        applyAndIncrement(event);
        uncommittedEvents.add(event);
    }

    private void applyAndIncrement(OrderEvent event) {
        applyEvent(event);
        sequenceNumber++;
    }

    private void applyEvent(OrderEvent event) {
        switch (event) {
            case OrderPlacedEvent e -> {
                this.id               = e.orderId();
                this.restaurantId     = e.restaurantId();
                this.customerName     = e.customerName();
                this.deliveryStreet   = e.deliveryStreet();
                this.deliveryNumber   = e.deliveryNumber();
                this.deliveryPostalCode = e.deliveryPostalCode();
                this.deliveryCity     = e.deliveryCity();
                this.deliveryCountry  = e.deliveryCountry();
                this.contactEmail     = e.contactEmail();
                this.items            = new ArrayList<>(e.items());
                this.createdAt        = e.occurredAt();
                this.status           = OrderStatus.PENDING_DECISION;
            }
            case OrderAcceptedEvent ignored          -> this.status = OrderStatus.ACCEPTED;
            case OrderRejectedEvent e -> {
                this.status          = OrderStatus.REJECTED;
                this.rejectionReason = e.reason();
            }
            case OrderReadyEvent ignored             -> this.status = OrderStatus.READY_FOR_PICKUP;
            case OrderPickedUpEvent ignored          -> this.status = OrderStatus.PICKED_UP;
            case OrderDeliveredEvent ignored         -> this.status = OrderStatus.DELIVERED;
            case CourierLocationUpdatedEvent e -> {
                this.courierLatitude  = e.latitude();
                this.courierLongitude = e.longitude();
            }
        }
    }

    public List<OrderEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(uncommittedEvents);
    }

    public void markEventsAsCommitted() {
        uncommittedEvents.clear();
    }

    public long getSequenceNumber() { return sequenceNumber; }

    // getters

    public UUID getId()                   { return id; }
    public UUID getRestaurantId()         { return restaurantId; }
    public String getCustomerName()       { return customerName; }
    public String getDeliveryStreet()     { return deliveryStreet; }
    public String getDeliveryNumber()     { return deliveryNumber; }
    public String getDeliveryPostalCode() { return deliveryPostalCode; }
    public String getDeliveryCity()       { return deliveryCity; }
    public String getDeliveryCountry()    { return deliveryCountry; }
    public String getContactEmail()       { return contactEmail; }
    public List<OrderItem> getItems()     { return items; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public OrderStatus getStatus()        { return status; }
    public String getRejectionReason()    { return rejectionReason; }
    public Double getCourierLatitude()    { return courierLatitude; }
    public Double getCourierLongitude()   { return courierLongitude; }
}
