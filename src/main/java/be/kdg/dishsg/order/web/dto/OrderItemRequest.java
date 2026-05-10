package be.kdg.dishsg.order.web.dto;

import java.util.UUID;

public class OrderItemRequest {
    public UUID dishId;
    public String dishName;
    public double price;
    public int quantity;
}
