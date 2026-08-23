package be.sebastiangondek.kdg.orders.adapters.out.eventStore;

import be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo.SpringDataOrderRepository;
import be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo.entities.OrderItemJpaEntity;
import be.sebastiangondek.kdg.orders.adapters.out.jpaRepositories.orderRepo.entities.OrderJpaEntity;
import be.sebastiangondek.kdg.orders.domain.Order;
import be.sebastiangondek.kdg.orders.domain.OrderItem;
import be.sebastiangondek.kdg.orders.domain.OrderSnapshot;
import be.sebastiangondek.kdg.orders.domain.OrderStatus;
import be.sebastiangondek.kdg.orders.domain.events.CourierLocationUpdatedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderAcceptedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderDeliveredEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderPickedUpEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderPlacedEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderReadyEvent;
import be.sebastiangondek.kdg.orders.domain.events.OrderRejectedEvent;
import be.sebastiangondek.kdg.orders.ports.out.OrderRepositoryPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/*
  Event-sourced order implementation.

  Write path  : Save each change as an event in order_events. Every
                SNAPSHOT_THRESHOLD events, save a snapshot too, and keep
                the orders table updated for fast reads (CQRS).

  Read by id  : Load the latest snapshot, then replay only the events
               after it — not the whole history.

  Query reads : Read straight from the orders table for speed.
 */
@Repository
@Transactional
public class OrderEventStoreAdapter implements OrderRepositoryPort {

    private static final int SNAPSHOT_THRESHOLD = 3;

    private final SpringDataOrderEventRepository eventRepo;
    private final SpringDataOrderSnapshotRepository snapshotRepo;
    private final SpringDataOrderRepository projectionRepo;
    private final ObjectMapper objectMapper;

    public OrderEventStoreAdapter(SpringDataOrderEventRepository eventRepo,
                                   SpringDataOrderSnapshotRepository snapshotRepo,
                                   SpringDataOrderRepository projectionRepo,
                                   ObjectMapper objectMapper) {
        this.eventRepo      = eventRepo;
        this.snapshotRepo   = snapshotRepo;
        this.projectionRepo = projectionRepo;
        this.objectMapper   = objectMapper;
    }

    //write

    @Override
    public Order save(Order order) {
        List<OrderEvent> newEvents = order.getUncommittedEvents();
        long baseSeq = order.getSequenceNumber() - newEvents.size();

        for (int i = 0; i < newEvents.size(); i++) {
            OrderEvent event = newEvents.get(i);
            long seqNum = baseSeq + i + 1;
            eventRepo.save(new OrderEventJpaEntity(
                    UUID.randomUUID(),
                    order.getId(),
                    event.getClass().getSimpleName(),
                    serialize(event),
                    seqNum,
                    event.occurredAt()
            ));
        }

        updateProjection(order);

        if (order.getSequenceNumber() % SNAPSHOT_THRESHOLD == 0) {
            takeSnapshot(order);
        }

        order.markEventsAsCommitted();
        return order;
    }

    // read by id

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(UUID id) {
        Optional<OrderSnapshotJpaEntity> latestSnapshot =
                snapshotRepo.findTopByOrderIdOrderBySequenceNumberDesc(id);

        if (latestSnapshot.isPresent()) {
            OrderSnapshotJpaEntity snapshotEntity = latestSnapshot.get();
            OrderSnapshot snapshot = deserializeSnapshot(snapshotEntity.getPayload());

            List<OrderEvent> subsequentEvents = eventRepo
                    .findByOrderIdAndSequenceNumberGreaterThanOrderBySequenceNumberAsc(
                            id, snapshotEntity.getSequenceNumber())
                    .stream()
                    .map(e -> deserializeEvent(e.getEventType(), e.getPayload()))
                    .collect(Collectors.toList());

            return Optional.of(Order.fromSnapshot(snapshot, subsequentEvents));
        }

        List<OrderEvent> allEvents = eventRepo
                .findByOrderIdOrderBySequenceNumberAsc(id)
                .stream()
                .map(e -> deserializeEvent(e.getEventType(), e.getPayload()))
                .collect(Collectors.toList());

        if (allEvents.isEmpty()) return Optional.empty();
        return Optional.of(Order.reconstitute(allEvents));
    }

    // query reads  (from projection)

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByRestaurantId(UUID restaurantId) {
        return projectionRepo.findByRestaurantId(restaurantId).stream()
                .map(this::projectionToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> findPendingOrderIdsBefore(LocalDateTime cutoff) {
        return projectionRepo.findByStatusAndCreatedAtBefore("PENDING_DECISION", cutoff)
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

    //snapshot helpers

    private void takeSnapshot(Order order) {
        OrderSnapshot snapshot = OrderSnapshot.of(order);
        snapshotRepo.save(new OrderSnapshotJpaEntity(
                UUID.randomUUID(),
                order.getId(),
                serialize(snapshot),
                order.getSequenceNumber(),
                LocalDateTime.now()
        ));
    }

    // projection helpers (CQRS read model)

    private void updateProjection(Order order) {
        List<OrderItemJpaEntity> items = order.getItems().stream()
                .map(item -> new OrderItemJpaEntity(
                        item.getId() != null ? item.getId() : UUID.randomUUID(),
                        order.getId(),
                        item.getDishId(),
                        item.getDishName(),
                        item.getPrice(),
                        item.getQuantity()))
                .collect(Collectors.toList());

        projectionRepo.save(new OrderJpaEntity(
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
                items));
    }

    // Map directly from the projection row
    private Order projectionToDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(i -> new OrderItem(i.getId(), i.getDishId(), i.getDishName(), i.getPrice(), i.getQuantity()))
                .collect(Collectors.toList());
        return Order.fromProjection(
                entity.getId(), entity.getRestaurantId(), entity.getCustomerName(),
                entity.getDeliveryStreet(), entity.getDeliveryNumber(), entity.getDeliveryPostalCode(),
                entity.getDeliveryCity(), entity.getDeliveryCountry(), entity.getContactEmail(),
                items, entity.getCreatedAt(), OrderStatus.valueOf(entity.getStatus()),
                entity.getRejectionReason(), entity.getCourierLatitude(), entity.getCourierLongitude());
    }

    //serialization
    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize: " + obj.getClass().getSimpleName(), e);
        }
    }

    private OrderEvent deserializeEvent(String eventType, String payload) {
        try {
            return switch (eventType) {
                case "OrderPlacedEvent"           -> objectMapper.readValue(payload, OrderPlacedEvent.class);
                case "OrderAcceptedEvent"         -> objectMapper.readValue(payload, OrderAcceptedEvent.class);
                case "OrderRejectedEvent"         -> objectMapper.readValue(payload, OrderRejectedEvent.class);
                case "OrderReadyEvent"            -> objectMapper.readValue(payload, OrderReadyEvent.class);
                case "OrderPickedUpEvent"         -> objectMapper.readValue(payload, OrderPickedUpEvent.class);
                case "OrderDeliveredEvent"        -> objectMapper.readValue(payload, OrderDeliveredEvent.class);
                case "CourierLocationUpdatedEvent" -> objectMapper.readValue(payload, CourierLocationUpdatedEvent.class);
                default -> throw new IllegalArgumentException("Unknown event type: " + eventType);
            };
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize event of type " + eventType, e);
        }
    }

    private OrderSnapshot deserializeSnapshot(String payload) {
        try {
            return objectMapper.readValue(payload, OrderSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize snapshot", e);
        }
    }
}
