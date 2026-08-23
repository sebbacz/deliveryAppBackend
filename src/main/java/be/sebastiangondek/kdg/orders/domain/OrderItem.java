package be.sebastiangondek.kdg.orders.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

// Snapshot of a dish at order-time; price is frozen here so menu changes don't affect past orders.
public class OrderItem {
    private final UUID id;
    private final UUID dishId;
    private final String dishName;
    private final double price;
    private final int quantity;

    @JsonCreator
    public OrderItem(
            @JsonProperty("id") UUID id,
            @JsonProperty("dishId") UUID dishId,
            @JsonProperty("dishName") String dishName,
            @JsonProperty("price") double price,
            @JsonProperty("quantity") int quantity) {
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
