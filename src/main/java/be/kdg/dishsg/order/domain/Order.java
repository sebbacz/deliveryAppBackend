package be.kdg.dishsg.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID restaurantId;
    private final String customerName;
    private final String deliveryStreet;
    private final String deliveryNumber;
    private final String deliveryPostalCode;
    private final String deliveryCity;
    private final String deliveryCountry;
    private final String contactEmail;
    private final List<OrderItem> items;
    private final LocalDateTime createdAt;
    private OrderStatus status;
    private String rejectionReason;
    private Double courierLatitude;
    private Double courierLongitude;

    public Order(UUID id, UUID restaurantId, String customerName,
                 String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                 String deliveryCity, String deliveryCountry, String contactEmail,
                 List<OrderItem> items, LocalDateTime createdAt, OrderStatus status,
                 String rejectionReason) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryNumber = deliveryNumber;
        this.deliveryPostalCode = deliveryPostalCode;
        this.deliveryCity = deliveryCity;
        this.deliveryCountry = deliveryCountry;
        this.contactEmail = contactEmail;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
        this.rejectionReason = rejectionReason;
    }

    public Order(UUID id, UUID restaurantId, String customerName,
                 String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                 String deliveryCity, String deliveryCountry, String contactEmail,
                 List<OrderItem> items, LocalDateTime createdAt, OrderStatus status,
                 String rejectionReason, Double courierLatitude, Double courierLongitude) {
        this(id, restaurantId, customerName, deliveryStreet, deliveryNumber, deliveryPostalCode,
                deliveryCity, deliveryCountry, contactEmail, items, createdAt, status, rejectionReason);
        this.courierLatitude = courierLatitude;
        this.courierLongitude = courierLongitude;
    }

    public void accept() {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be accepted");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void reject(String reason) {
        if (status != OrderStatus.PENDING_DECISION) {
            throw new IllegalStateException("Only pending orders can be rejected");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }
        this.status = OrderStatus.REJECTED;
        this.rejectionReason = reason;
    }

    public void markReady() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("Only accepted orders can be marked ready for pickup");
        }
        this.status = OrderStatus.READY_FOR_PICKUP;
    }

    public void markPickedUp() {
        if (status != OrderStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Only orders ready for pickup can be marked as picked up");
        }
        this.status = OrderStatus.PICKED_UP;
    }

    public void markDelivered() {
        if (status != OrderStatus.PICKED_UP) {
            throw new IllegalStateException("Only picked up orders can be marked as delivered");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void updateCourierLocation(double latitude, double longitude) {
        this.courierLatitude = latitude;
        this.courierLongitude = longitude;
    }

    public UUID getId()                  { return id; }
    public UUID getRestaurantId()        { return restaurantId; }
    public String getCustomerName()      { return customerName; }
    public String getDeliveryStreet()    { return deliveryStreet; }
    public String getDeliveryNumber()    { return deliveryNumber; }
    public String getDeliveryPostalCode(){ return deliveryPostalCode; }
    public String getDeliveryCity()      { return deliveryCity; }
    public String getDeliveryCountry()   { return deliveryCountry; }
    public String getContactEmail()      { return contactEmail; }
    public List<OrderItem> getItems()    { return items; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
    public OrderStatus getStatus()       { return status; }
    public String getRejectionReason()   { return rejectionReason; }
    public Double getCourierLatitude()   { return courierLatitude; }
    public Double getCourierLongitude()  { return courierLongitude; }
}
