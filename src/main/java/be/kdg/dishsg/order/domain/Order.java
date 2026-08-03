package be.kdg.dishsg.order.domain;

import be.kdg.dishsg.order.domain.event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Event-sourced Order aggregate root.
 *
 * <p>State is <em>never</em> mutated directly — every business decision raises
 * a domain event via {@link #raiseEvent(OrderEvent)}, which applies the event
 * to the aggregate state and adds it to {@link #pendingEvents}.  The repository
 * then persists those events and updates the read projection.
 *
 * <p>To reconstitute an Order from storage, use one of the static factory
 * methods: {@link #reconstitute(List)} or
 * {@link #fromSnapshot(OrderSnapshotState, List)}.
 */
public class Order {

    // ── Aggregate state ───────────────────────────────────────────────────────

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

    // ── Event-sourcing metadata ───────────────────────────────────────────────

    /** Monotonically increasing version; equals the sequence number of the last applied event. */
    private long version = 0;

    /** Version at the time the aggregate was loaded (before new events are raised). */
    private long baseVersion = 0;

    /** Events raised during this unit-of-work, not yet persisted. */
    private final List<OrderEvent> pendingEvents = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Private no-arg constructor — all callers must go through a factory method. */
    private Order() {}

    /**
     * Reconstruct a read-only Order view from the JPA projection table.
     * These instances must not have commands issued on them; use
     * {@link #reconstitute(List)} or {@link #fromSnapshot} for write-path loads.
     */
    public static Order fromProjection(UUID id, UUID restaurantId, String customerName,
                                       String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                                       String deliveryCity, String deliveryCountry, String contactEmail,
                                       List<OrderItem> items, LocalDateTime createdAt, OrderStatus status,
                                       String rejectionReason, Double courierLatitude, Double courierLongitude) {
        Order o = new Order();
        o.id = id;
        o.restaurantId = restaurantId;
        o.customerName = customerName;
        o.deliveryStreet = deliveryStreet;
        o.deliveryNumber = deliveryNumber;
        o.deliveryPostalCode = deliveryPostalCode;
        o.deliveryCity = deliveryCity;
        o.deliveryCountry = deliveryCountry;
        o.contactEmail = contactEmail;
        o.items = items;
        o.createdAt = createdAt;
        o.status = status;
        o.rejectionReason = rejectionReason;
        o.courierLatitude = courierLatitude;
        o.courierLongitude = courierLongitude;
        return o;
    }

    // ── Event-sourced factory methods ─────────────────────────────────────────

    /**
     * Create a brand-new order.  Raises {@link OrderCreatedEvent}.
     */
    public static Order create(UUID id, UUID restaurantId, String customerName,
                               String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                               String deliveryCity, String deliveryCountry, String contactEmail,
                               List<OrderItem> items, LocalDateTime createdAt) {
        Order order = new Order();
        List<OrderCreatedEvent.ItemData> itemData = items.stream()
                .map(i -> new OrderCreatedEvent.ItemData(
                        i.getId() != null ? i.getId() : UUID.randomUUID(),
                        i.getDishId(), i.getDishName(), i.getPrice(), i.getQuantity()))
                .toList();
        order.raiseEvent(new OrderCreatedEvent(
                id, restaurantId, customerName,
                deliveryStreet, deliveryNumber, deliveryPostalCode, deliveryCity, deliveryCountry,
                contactEmail, itemData, createdAt, LocalDateTime.now()));
        return order;
    }

    /**
     * Reconstitute an order by replaying its complete event history.
     */
    public static Order reconstitute(List<OrderEvent> events) {
        if (events.isEmpty()) throw new IllegalArgumentException("Cannot reconstitute order from empty event stream");
        Order order = new Order();
        for (OrderEvent event : events) {
            order.applyEvent(event);
            order.version++;
        }
        order.baseVersion = order.version;
        return order;
    }

    /**
     * Reconstitute an order from a snapshot plus any events recorded after it.
     */
    public static Order fromSnapshot(OrderSnapshotState snapshot, List<OrderEvent> eventsAfterSnapshot) {
        Order order = new Order();
        order.id              = snapshot.id();
        order.restaurantId    = snapshot.restaurantId();
        order.customerName    = snapshot.customerName();
        order.deliveryStreet  = snapshot.deliveryStreet();
        order.deliveryNumber  = snapshot.deliveryNumber();
        order.deliveryPostalCode = snapshot.deliveryPostalCode();
        order.deliveryCity    = snapshot.deliveryCity();
        order.deliveryCountry = snapshot.deliveryCountry();
        order.contactEmail    = snapshot.contactEmail();
        order.items = snapshot.items().stream()
                .map(i -> new OrderItem(i.id(), i.dishId(), i.dishName(), i.price(), i.quantity()))
                .toList();
        order.createdAt       = snapshot.createdAt();
        order.status          = OrderStatus.valueOf(snapshot.status());
        order.rejectionReason = snapshot.rejectionReason();
        order.courierLatitude = snapshot.courierLatitude();
        order.courierLongitude = snapshot.courierLongitude();
        order.version         = snapshot.version();
        order.baseVersion     = snapshot.version();

        for (OrderEvent event : eventsAfterSnapshot) {
            order.applyEvent(event);
            order.version++;
        }
        order.baseVersion = order.version;
        return order;
    }

    // ── Event application (pure state projection) ─────────────────────────────

    private void applyEvent(OrderEvent event) {
        switch (event) {
            case OrderCreatedEvent e -> {
                id              = e.orderId();
                restaurantId    = e.restaurantId();
                customerName    = e.customerName();
                deliveryStreet  = e.deliveryStreet();
                deliveryNumber  = e.deliveryNumber();
                deliveryPostalCode = e.deliveryPostalCode();
                deliveryCity    = e.deliveryCity();
                deliveryCountry = e.deliveryCountry();
                contactEmail    = e.contactEmail();
                items = e.items().stream()
                        .map(i -> new OrderItem(i.id(), i.dishId(), i.dishName(), i.price(), i.quantity()))
                        .toList();
                createdAt = e.createdAt();
                status    = OrderStatus.PENDING_DECISION;
            }
            case OrderAcceptedEvent ignored            -> status = OrderStatus.ACCEPTED;
            case OrderRejectedEvent e -> {
                status          = OrderStatus.REJECTED;
                rejectionReason = e.reason();
            }
            case OrderMarkedReadyForPickupEvent ignored -> status = OrderStatus.READY_FOR_PICKUP;
            case OrderPickedUpEvent ignored             -> status = OrderStatus.PICKED_UP;
            case OrderDeliveredEvent ignored            -> status = OrderStatus.DELIVERED;
            case OrderCourierLocationUpdatedEvent e -> {
                courierLatitude  = e.latitude();
                courierLongitude = e.longitude();
            }
        }
    }

    private void raiseEvent(OrderEvent event) {
        applyEvent(event);
        version++;
        pendingEvents.add(event);
    }

    // ── Command methods ───────────────────────────────────────────────────────

    public void accept() {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be accepted");
        }
        raiseEvent(new OrderAcceptedEvent(id, LocalDateTime.now()));
    }

    public void reject(String reason) {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be rejected");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }
        raiseEvent(new OrderRejectedEvent(id, reason, LocalDateTime.now()));
    }

    public void markReady() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("Only accepted orders can be marked ready for pickup");
        }
        raiseEvent(new OrderMarkedReadyForPickupEvent(id, LocalDateTime.now()));
    }

    public void markPickedUp() {
        if (status != OrderStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Only orders ready for pickup can be marked as picked up");
        }
        raiseEvent(new OrderPickedUpEvent(id, LocalDateTime.now()));
    }

    public void markDelivered() {
        if (status != OrderStatus.PICKED_UP) {
            throw new IllegalStateException("Only picked up orders can be marked as delivered");
        }
        raiseEvent(new OrderDeliveredEvent(id, LocalDateTime.now()));
    }

    public void updateCourierLocation(double latitude, double longitude) {
        raiseEvent(new OrderCourierLocationUpdatedEvent(id, latitude, longitude, LocalDateTime.now()));
    }

    // ── Event-sourcing accessors ──────────────────────────────────────────────

    public List<OrderEvent> getPendingEvents() { return Collections.unmodifiableList(pendingEvents); }
    public void clearPendingEvents()            { pendingEvents.clear(); }
    public long getVersion()                    { return version; }
    public long getBaseVersion()                { return baseVersion; }

    /** Produces a snapshot of the current aggregate state for storage. */
    public OrderSnapshotState toSnapshot() {
        List<OrderSnapshotState.ItemData> snapshotItems = items.stream()
                .map(i -> new OrderSnapshotState.ItemData(
                        i.getId(), i.getDishId(), i.getDishName(), i.getPrice(), i.getQuantity()))
                .toList();
        return new OrderSnapshotState(
                id, restaurantId, customerName,
                deliveryStreet, deliveryNumber, deliveryPostalCode, deliveryCity, deliveryCountry,
                contactEmail, snapshotItems, createdAt,
                status.name(), rejectionReason, courierLatitude, courierLongitude,
                version);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

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
