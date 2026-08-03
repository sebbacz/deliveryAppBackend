package be.kdg.dishsg.order.adapters.out.eventStore;

import be.kdg.dishsg.order.adapters.out.eventStore.entities.OrderEventJpaEntity;
import be.kdg.dishsg.order.adapters.out.eventStore.entities.OrderSnapshotJpaEntity;
import be.kdg.dishsg.order.domain.OrderSnapshotState;
import be.kdg.dishsg.order.domain.event.OrderEvent;
import be.kdg.dishsg.order.ports.out.OrderEventStorePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// JPA adapter for the event store: serializes/deserializes order events and snapshots to/from JSON.
@Component
@Transactional
public class JpaOrderEventStoreAdapter implements OrderEventStorePort {

    private final SpringDataOrderEventRepository eventRepo;
    private final SpringDataOrderSnapshotRepository snapshotRepo;
    private final ObjectMapper objectMapper;

    public JpaOrderEventStoreAdapter(SpringDataOrderEventRepository eventRepo,
                                     SpringDataOrderSnapshotRepository snapshotRepo,
                                     ObjectMapper objectMapper) {
        this.eventRepo = eventRepo;
        this.snapshotRepo = snapshotRepo;
        this.objectMapper = objectMapper;
    }

    @Override
    public void appendEvents(UUID orderId, List<OrderEvent> events, long expectedVersion) {
        long sequenceNr = expectedVersion;
        for (OrderEvent event : events) {
            sequenceNr++;
            String payload = serialize(event);
            eventRepo.save(new OrderEventJpaEntity(
                    orderId,
                    event.getClass().getSimpleName(),
                    payload,
                    sequenceNr,
                    event.occurredAt()
            ));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEvent> loadEvents(UUID orderId, long afterVersion) {
        return eventRepo
                .findByOrderIdAndSequenceNrGreaterThanOrderBySequenceNrAsc(orderId, afterVersion)
                .stream()
                .map(entity -> deserializeEvent(entity.getPayload()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveSnapshot(UUID orderId, OrderSnapshotState snapshot) {
        String data = serialize(snapshot);
        snapshotRepo.findById(orderId).ifPresentOrElse(
                existing -> {
                    existing.setSnapshotData(data);
                    existing.setVersion(snapshot.version());
                    existing.setCreatedAt(LocalDateTime.now());
                },
                () -> snapshotRepo.save(
                        new OrderSnapshotJpaEntity(orderId, data, snapshot.version(), LocalDateTime.now()))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderSnapshotState> loadLatestSnapshot(UUID orderId) {
        return snapshotRepo.findById(orderId)
                .map(entity -> deserialize(entity.getSnapshotData(), OrderSnapshotState.class));
    }

    // ── Serialization helpers ─────────────────────────────────────────────────

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize " + obj.getClass().getSimpleName(), e);
        }
    }

    private OrderEvent deserializeEvent(String json) {
        try {
            return objectMapper.readValue(json, OrderEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize order event", e);
        }
    }

    private <T> T deserialize(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize " + type.getSimpleName(), e);
        }
    }
}
