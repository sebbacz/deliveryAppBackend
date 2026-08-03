package be.kdg.dishsg.order.adapters.out.jpaRepositories.orderRepo;

import be.kdg.dishsg.order.adapters.out.jpaRepositories.orderRepo.entities.OrderItemJpaEntity;
import be.kdg.dishsg.order.adapters.out.jpaRepositories.orderRepo.entities.OrderJpaEntity;
import be.kdg.dishsg.order.domain.Order;
import be.kdg.dishsg.order.domain.OrderItem;
import be.kdg.dishsg.order.domain.OrderSnapshotState;
import be.kdg.dishsg.order.domain.OrderStatus;
import be.kdg.dishsg.order.domain.event.OrderEvent;
import be.kdg.dishsg.order.ports.out.OrderEventStorePort;
import be.kdg.dishsg.order.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Event-sourced implementation of {@link OrderRepositoryPort}.
 *
 * <p><strong>Write path</strong> — pending events are appended to the
 * {@code order_events} table.  A snapshot is taken every
 * {@value #SNAPSHOT_THRESHOLD} events to bound replay time.
 *
 * <p><strong>Read path</strong> — individual aggregate loads reconstruct state
 * by replaying the event stream (starting from the latest snapshot, if any).
 * List / count queries use the {@code orders} projection table for efficiency.
 */
@Repository
@Transactional
public class OrderJpaAdapter implements OrderRepositoryPort {

    private static final int SNAPSHOT_THRESHOLD = 5;

    private final OrderEventStorePort eventStore;
    private final SpringDataOrderRepository projectionRepo;

    public OrderJpaAdapter(OrderEventStorePort eventStore,
                           SpringDataOrderRepository projectionRepo) {
        this.eventStore = eventStore;
        this.projectionRepo = projectionRepo;
    }

    // ── Write ─────────────────────────────────────────────────────────────────

    @Override
    public Order save(Order order) {
        List<OrderEvent> pending = order.getPendingEvents();
        if (pending.isEmpty()) return order;

        eventStore.appendEvents(order.getId(), pending, order.getBaseVersion());
        projectionRepo.save(toProjectionEntity(order));

        long newVersion = order.getVersion();
        if (newVersion % SNAPSHOT_THRESHOLD == 0) {
            eventStore.saveSnapshot(order.getId(), order.toSnapshot());
        }

        order.clearPendingEvents();
        return order;
    }

    // ── Read (aggregate reconstitution from event store) ──────────────────────

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(UUID id) {
        Optional<OrderSnapshotState> snapshot = eventStore.loadLatestSnapshot(id);
        long fromVersion = snapshot.map(OrderSnapshotState::version).orElse(0L);
        List<OrderEvent> events = eventStore.loadEvents(id, fromVersion);

        if (snapshot.isEmpty() && events.isEmpty()) return Optional.empty();

        Order order = snapshot.isPresent()
                ? Order.fromSnapshot(snapshot.get(), events)
                : Order.reconstitute(events);
        return Optional.of(order);
    }

    // ── Read (projection queries) ─────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByRestaurantId(UUID restaurantId) {
        return projectionRepo.findByRestaurantId(restaurantId).stream()
                .map(this::fromProjection)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> findPendingOrderIdsBefore(LocalDateTime cutoff) {
        return projectionRepo
                .findByStatusAndCreatedAtBefore("PENDING_DECISION", cutoff)
                .stream()
                .map(OrderJpaEntity::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int countActiveByRestaurantId(UUID restaurantId) {
        return projectionRepo.countByRestaurantIdAndStatusIn(
                restaurantId, List.of("PENDING_DECISION", "ACCEPTED"));
    }

    // ── Mapping helpers ───────────────────────────────────────────────────────

    private OrderJpaEntity toProjectionEntity(Order order) {
        List<OrderItemJpaEntity> items = order.getItems().stream()
                .map(item -> new OrderItemJpaEntity(
                        item.getId() != null ? item.getId() : UUID.randomUUID(),
                        order.getId(),
                        item.getDishId(),
                        item.getDishName(),
                        item.getPrice(),
                        item.getQuantity()))
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
                order.getCourierLatitude(),
                order.getCourierLongitude(),
                items);
    }

    /**
     * Reconstruct a read-only Order view from the projection table.
     * These instances must not have commands issued on them.
     */
    private Order fromProjection(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(i -> new OrderItem(i.getId(), i.getDishId(), i.getDishName(), i.getPrice(), i.getQuantity()))
                .collect(Collectors.toList());
        return Order.fromProjection(
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
                entity.getRejectionReason(),
                entity.getCourierLatitude(),
                entity.getCourierLongitude());
    }
}
