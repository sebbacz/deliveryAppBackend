package be.kdg.dishsg.order.web.dto;

import java.util.UUID;

public class OrderItemResponse {
    public UUID id;
    public UUID dishId;
    public String dishName;
    public double price;
    public int quantity;
}
