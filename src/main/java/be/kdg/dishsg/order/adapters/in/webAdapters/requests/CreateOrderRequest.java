package be.kdg.dishsg.order.adapters.in.webAdapters.requests;

import java.util.List;
import java.util.UUID;

// Request body for placing a new order, including delivery address, contact email, and line items.
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
