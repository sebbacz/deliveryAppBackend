package be.sebastiangondek.kdg.orders.adapters.in.webAdapters;

import be.sebastiangondek.kdg.orders.adapters.in.dto.OrderItemResponse;
import be.sebastiangondek.kdg.orders.adapters.in.dto.OrderResponse;
import be.sebastiangondek.kdg.orders.adapters.in.webAdapters.requests.CreateOrderRequest;
import be.sebastiangondek.kdg.orders.adapters.in.webAdapters.requests.RejectOrderRequest;
import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.ports.in.AcceptOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.AcceptOrderUseCase;
import be.sebastiangondek.kdg.orders.ports.in.CreateOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.CreateOrderUseCase;
import be.sebastiangondek.kdg.orders.ports.in.GetOrdersUseCase;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderDeliveredUseCase;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderPickedUpUseCase;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderReadyCmd;
import be.sebastiangondek.kdg.orders.ports.in.MarkOrderReadyUseCase;
import be.sebastiangondek.kdg.orders.ports.in.RejectOrderCmd;
import be.sebastiangondek.kdg.orders.ports.in.RejectOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Authenticated REST controller for restaurant owners to manage the order
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;
    private final MarkOrderReadyUseCase markOrderReadyUseCase;
    private final MarkOrderPickedUpUseCase markOrderPickedUpUseCase;
    private final MarkOrderDeliveredUseCase markOrderDeliveredUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           AcceptOrderUseCase acceptOrderUseCase,
                           RejectOrderUseCase rejectOrderUseCase,
                           GetOrdersUseCase getOrdersUseCase,
                           MarkOrderReadyUseCase markOrderReadyUseCase,
                           MarkOrderPickedUpUseCase markOrderPickedUpUseCase,
                           MarkOrderDeliveredUseCase markOrderDeliveredUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.acceptOrderUseCase = acceptOrderUseCase;
        this.rejectOrderUseCase = rejectOrderUseCase;
        this.getOrdersUseCase = getOrdersUseCase;
        this.markOrderReadyUseCase = markOrderReadyUseCase;
        this.markOrderPickedUpUseCase = markOrderPickedUpUseCase;
        this.markOrderDeliveredUseCase = markOrderDeliveredUseCase;
    }

    // Create order (customer flow, no auth required)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        List<CreateOrderCmd.OrderItemCmd> items = request.items.stream()
                .map(i -> new CreateOrderCmd.OrderItemCmd(i.dishId, i.dishName, i.price, i.quantity))
                .collect(Collectors.toList());
        CreateOrderCmd cmd = new CreateOrderCmd(
                request.restaurantId,
                request.customerName,
                request.deliveryStreet,
                request.deliveryNumber,
                request.deliveryPostalCode,
                request.deliveryCity,
                request.deliveryCountry,
                request.contactEmail,
                items
        );
        return toResponse(createOrderUseCase.createOrder(cmd));
    }

    //  Get all orders for owner's restaurant
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public List<OrderResponse> getOrders(@PathVariable UUID restaurantId) {
        return getOrdersUseCase.getOrdersForRestaurant(restaurantId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    //  Accept an order
    @PostMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void acceptOrder(@PathVariable UUID id) {
        acceptOrderUseCase.acceptOrder(new AcceptOrderCmd(id));
    }

    // Reject an order with reason
    @PostMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void rejectOrder(@PathVariable UUID id, @RequestBody RejectOrderRequest request) {
        rejectOrderUseCase.rejectOrder(new RejectOrderCmd(id, request.reason));
    }

    //  Mark accepted order as ready for pickup
    @PostMapping("/{id}/ready")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void markOrderReady(@PathVariable UUID id) {
        markOrderReadyUseCase.markOrderReady(new MarkOrderReadyCmd(id));
    }

    // Simulate delivery service: mark order as picked up by courier
    @PostMapping("/{id}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void markOrderPickedUp(@PathVariable UUID id) {
        markOrderPickedUpUseCase.markOrderPickedUp(new MarkOrderPickedUpCmd(id));
    }

    // Simulate delivery service: mark order as delivered
    @PostMapping("/{id}/delivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void markOrderDelivered(@PathVariable UUID id) {
        markOrderDeliveredUseCase.markOrderDelivered(new MarkOrderDeliveredCmd(id));
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
