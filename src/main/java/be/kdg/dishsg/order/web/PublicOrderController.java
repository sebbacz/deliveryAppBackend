package be.kdg.dishsg.order.web;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderItem;
import be.kdg.dishsg.order.domain.OrderStatus;
import be.kdg.dishsg.order.ports.in.CreateOrderUseCase;
import be.kdg.dishsg.order.ports.in.GetBusynessUseCase;
import be.kdg.dishsg.order.ports.in.GetOrderByIdUseCase;
import be.kdg.dishsg.order.web.dto.BusynessResponse;
import be.kdg.dishsg.order.web.dto.CreateOrderRequest;
import be.kdg.dishsg.order.web.dto.OrderItemResponse;
import be.kdg.dishsg.order.web.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/unsecured/orders")
public class PublicOrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetBusynessUseCase getBusynessUseCase;

    public PublicOrderController(CreateOrderUseCase createOrderUseCase,
                                  GetOrderByIdUseCase getOrderByIdUseCase,
                                  GetBusynessUseCase getBusynessUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.getBusynessUseCase = getBusynessUseCase;
    }

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

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable UUID id) {
        return toResponse(getOrderByIdUseCase.getOrderById(id));
    }

    @GetMapping("/restaurant/{restaurantId}/busyness")
    public BusynessResponse getBusyness(@PathVariable UUID restaurantId) {
        return new BusynessResponse(getBusynessUseCase.getActiveOrderCount(restaurantId));
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
        dto.courierLatitude = order.getCourierLatitude();
        dto.courierLongitude = order.getCourierLongitude();
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
