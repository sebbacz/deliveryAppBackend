package be.kdg.dishsg.order.domain;

import java.util.UUID;

public class OrderItem {
    private final UUID id;
    private final UUID dishId;
    private final String dishName;
    private final double price;
    private final int quantity;

    public OrderItem(UUID id, UUID dishId, String dishName, double price, int quantity) {
        this.id = id;
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId()          { return id; }
    public UUID getDishId()      { return dishId; }
    public String getDishName()  { return dishName; }
    public double getPrice()     { return price; }
    public int getQuantity()     { return quantity; }
}
