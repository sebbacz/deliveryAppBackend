package be.kdg.dishsg.order.adapter.out.persistence;

import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderItem;
import be.kdg.dishsg.order.domain.OrderStatus;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository springRepo;

    public OrderPersistenceAdapter(SpringDataOrderRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Order save(Order order) {
        springRepo.save(toEntity(order));
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return springRepo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findByRestaurantId(UUID restaurantId) {
        return springRepo.findByRestaurantId(restaurantId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findPendingOrdersBefore(LocalDateTime cutoff) {
        return springRepo.findByStatusAndCreatedAtBefore("PENDING_DECISION", cutoff).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveByRestaurantId(UUID restaurantId) {
        return springRepo.countByRestaurantIdAndStatusIn(restaurantId, List.of("PENDING_DECISION", "ACCEPTED"));
    }

    private OrderJpaEntity toEntity(Order order) {
        List<OrderItemJpaEntity> items = order.getItems().stream()
                .map(item -> new OrderItemJpaEntity(
                        item.getId() != null ? item.getId() : UUID.randomUUID(),
                        order.getId(),
                        item.getDishId(),
                        item.getDishName(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
        return new OrderJpaEntity(
                order.getId(),
                order.getRestaurantId(),
                order.getCustomerName(),
                order.getDeliveryStreet(),
                order.getDeliveryNumber(),
                order.getDeliveryPostalCode(),
                order.getDeliveryCity(),
                order.getDeliveryCountry(),
                order.getContactEmail(),
                order.getStatus().name(),
                order.getRejectionReason(),
                order.getCreatedAt(),
                items
        );
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(i -> new OrderItem(i.getId(), i.getDishId(), i.getDishName(), i.getPrice(), i.getQuantity()))
                .collect(Collectors.toList());
        return new Order(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getCustomerName(),
                entity.getDeliveryStreet(),
                entity.getDeliveryNumber(),
                entity.getDeliveryPostalCode(),
                entity.getDeliveryCity(),
                entity.getDeliveryCountry(),
                entity.getContactEmail(),
                items,
                entity.getCreatedAt(),
                OrderStatus.valueOf(entity.getStatus()),
                entity.getRejectionReason()
        );
    }
}
