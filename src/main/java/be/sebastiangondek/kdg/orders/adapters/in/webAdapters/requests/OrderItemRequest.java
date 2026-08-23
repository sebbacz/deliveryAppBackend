package be.sebastiangondek.kdg.orders.adapters.in.webAdapters.requests;

import java.util.UUID;

// Request DTO for a single dish line item when placing an order.
public class OrderItemRequest {
    public UUID dishId;
    public String dishName;
    public double price;
    public int quantity;
}
