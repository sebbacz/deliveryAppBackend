package be.kdg.dishsg.domain.order;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private final UUID dishId;
    private final String dishName;
    private final BigDecimal priceAtCheckout;
    private final int quantity;

    public OrderItem(UUID dishId, String dishName, BigDecimal priceAtCheckout, int quantity) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.priceAtCheckout = priceAtCheckout;
        this.quantity = quantity;
    }
}
