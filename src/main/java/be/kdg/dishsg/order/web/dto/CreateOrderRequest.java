package be.kdg.dishsg.order.web.dto;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    public UUID restaurantId;
    public String customerName;
    public String deliveryStreet;
    public String deliveryNumber;
    public String deliveryPostalCode;
    public String deliveryCity;
    public String deliveryCountry;
    public String contactEmail;
    public List<OrderItemRequest> items;
}
