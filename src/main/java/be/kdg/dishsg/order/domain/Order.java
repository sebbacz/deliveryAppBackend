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
}
