package be.sebastiangondek.kdg.orders.adapters.in.dto;

import java.util.UUID;

// Response DTO for a single order line item returned inside an OrderResponse.
public class OrderItemResponse {
    public UUID id;
    public UUID dishId;
    public String dishName;
    public double price;
    public int quantity;
}
