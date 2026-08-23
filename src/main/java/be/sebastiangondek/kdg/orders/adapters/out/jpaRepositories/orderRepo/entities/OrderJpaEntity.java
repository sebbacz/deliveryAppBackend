package be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// JPA  entity forfr orders
@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    private String deliveryStreet;
    private String deliveryNumber;
    private String deliveryPostalCode;
    private String deliveryCity;
    private String deliveryCountry;
    private String contactEmail;

    @Column(nullable = false)
    private String status;

    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Double courierLatitude;
    private Double courierLongitude;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderItemJpaEntity> items;

    protected OrderJpaEntity() {}

    public OrderJpaEntity(UUID id, UUID restaurantId, String customerName,
                          String deliveryStreet, String deliveryNumber, String deliveryPostalCode,
                          String deliveryCity, String deliveryCountry, String contactEmail,
                          String status, String rejectionReason, LocalDateTime createdAt,
                          Double courierLatitude, Double courierLongitude,
                          List<OrderItemJpaEntity> items) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryNumber = deliveryNumber;
        this.deliveryPostalCode = deliveryPostalCode;
        this.deliveryCity = deliveryCity;
        this.deliveryCountry = deliveryCountry;
        this.contactEmail = contactEmail;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
        this.courierLatitude = courierLatitude;
        this.courierLongitude = courierLongitude;
        this.items = items;
    }

    public UUID getId()                   { return id; }
    public UUID getRestaurantId()         { return restaurantId; }
    public String getCustomerName()       { return customerName; }
    public String getDeliveryStreet()     { return deliveryStreet; }
    public String getDeliveryNumber()     { return deliveryNumber; }
    public String getDeliveryPostalCode() { return deliveryPostalCode; }
    public String getDeliveryCity()       { return deliveryCity; }
    public String getDeliveryCountry()    { return deliveryCountry; }
    public String getContactEmail()       { return contactEmail; }
    public String getStatus()             { return status; }
    public String getRejectionReason()    { return rejectionReason; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public Double getCourierLatitude()    { return courierLatitude; }
    public Double getCourierLongitude()   { return courierLongitude; }
    public List<OrderItemJpaEntity> getItems() { return items; }
}
