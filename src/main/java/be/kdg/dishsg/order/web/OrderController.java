package be.kdg.dishsg.order.web;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderItem;
import be.kdg.dishsg.order.domain.OrderStatus;
import be.kdg.dishsg.order.ports.in.AcceptOrderUseCase;
import be.kdg.dishsg.order.ports.in.CreateOrderUseCase;
import be.kdg.dishsg.order.ports.in.GetOrdersUseCase;
import be.kdg.dishsg.order.ports.in.RejectOrderUseCase;
import be.kdg.dishsg.order.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           AcceptOrderUseCase acceptOrderUseCase,
                           RejectOrderUseCase rejectOrderUseCase,
                           GetOrdersUseCase getOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.acceptOrderUseCase = acceptOrderUseCase;
        this.rejectOrderUseCase = rejectOrderUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
    }

    // Create order (customer flow, no auth required)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        List<OrderItem> items = request.items.stream()
                .map(i -> new OrderItem(UUID.randomUUID(), i.dishId, i.dishName, i.price, i.quantity))
                .collect(Collectors.toList());
        Order order = new Order(null, request.restaurantId, request.customerName,
                request.deliveryStreet, request.deliveryNumber, request.deliveryPostalCode,
                request.deliveryCity, request.deliveryCountry, request.contactEmail,
                items, null, OrderStatus.PENDING_DECISION, null);
        return toResponse(createOrderUseCase.createOrder(order));
    }

    // US10: Get all orders for owner's restaurant
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public List<OrderResponse> getOrders(@PathVariable UUID restaurantId) {
        return getOrdersUseCase.getOrdersForRestaurant(restaurantId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // US10: Accept an order
    @PostMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void acceptOrder(@PathVariable UUID id) {
        acceptOrderUseCase.acceptOrder(id);
    }

    // US10: Reject an order with reason
    @PostMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void rejectOrder(@PathVariable UUID id, @RequestBody RejectOrderRequest request) {
        rejectOrderUseCase.rejectOrder(id, request.reason);
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.id = order.getId();
        dto.restaurantId = order.getRestaurantId();
        dto.customerName = order.getCustomerName();
        dto.deliveryStreet = order.getDeliveryStreet();
        dto.deliveryNumber = order.getDeliveryNumber();
        dto.deliveryPostalCode = order.getDeliveryPostalCode();
        dto.deliveryCity = order.getDeliveryCity();
        dto.deliveryCountry = order.getDeliveryCountry();
        dto.contactEmail = order.getContactEmail();
        dto.status = order.getStatus().name();
        dto.rejectionReason = order.getRejectionReason();
        dto.createdAt = order.getCreatedAt();
        dto.items = order.getItems().stream().map(i -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.id = i.getId();
            ir.dishId = i.getDishId();
            ir.dishName = i.getDishName();
            ir.price = i.getPrice();
            ir.quantity = i.getQuantity();
            return ir;
        }).collect(Collectors.toList());
        return dto;
    }
}
