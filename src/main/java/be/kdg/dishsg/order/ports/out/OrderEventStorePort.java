package be.kdg.dishsg.order.ports.out;

import be.kdg.dishsg.order.domain.OrderSnapshotState;
import be.kdg.dishsg.order.domain.event.OrderEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Out-port for the event store: append events, load events since a version, and snapshot the aggregate.
public interface OrderEventStorePort {

    /**
     * Append new events starting at expectedVersion + 1.
     */
    void appendEvents(UUID orderId, List<OrderEvent> events, long expectedVersion);

    /**
     * Load all events for the aggregate whose sequence number is strictly
     * greater than {@code afterVersion}.
     */
    List<OrderEvent> loadEvents(UUID orderId, long afterVersion);

    /**
     * Persist (upsert) a snapshot for the aggregate.
     */
    void saveSnapshot(UUID orderId, OrderSnapshotState snapshot);

    /**
     * Return the most recent snapshot, if one exists.
     */
    Optional<OrderSnapshotState> loadLatestSnapshot(UUID orderId);
}
