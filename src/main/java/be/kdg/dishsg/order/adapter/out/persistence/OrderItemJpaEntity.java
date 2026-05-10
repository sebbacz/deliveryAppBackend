package be.kdg.dishsg.order.adapter.out.persistence;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {

    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "dish_id", nullable = false)
    private UUID dishId;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    protected OrderItemJpaEntity() {}

    public OrderItemJpaEntity(UUID id, UUID orderId, UUID dishId, String dishName, double price, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId()         { return id; }
    public UUID getOrderId()    { return orderId; }
    public UUID getDishId()     { return dishId; }
    public String getDishName() { return dishName; }
    public double getPrice()    { return price; }
    public int getQuantity()    { return quantity; }
}
