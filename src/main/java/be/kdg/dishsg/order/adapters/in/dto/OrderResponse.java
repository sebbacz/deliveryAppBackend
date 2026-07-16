package be.kdg.dishsg.order.adapters.in.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Response DTO representing a full order, including status, line items, and courier GPS position.
public class OrderResponse {
    public UUID id;
    public UUID restaurantId;
    public String customerName;
    public String deliveryStreet;
    public String deliveryNumber;
    public String deliveryPostalCode;
    public String deliveryCity;
    public String deliveryCountry;
    public String contactEmail;
    public String status;
    public String rejectionReason;
    public LocalDateTime createdAt;
    public List<OrderItemResponse> items;
    public Double courierLatitude;
    public Double courierLongitude;
}
