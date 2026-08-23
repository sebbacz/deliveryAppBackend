package be.sebastiangondek.kdg.orders.adapters.out.eventStore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

// JPA entity for the order event log; rows are never updated or deleted.
@Entity
@Table(name = "order_events")
public class OrderEventJpaEntity {

    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(name = "sequence_number", nullable = false)
    private long sequenceNumber;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    protected OrderEventJpaEntity() {}

    public OrderEventJpaEntity(UUID id, UUID orderId, String eventType,
                                String payload, long sequenceNumber, LocalDateTime occurredAt) {
        this.id             = id;
        this.orderId        = orderId;
        this.eventType      = eventType;
        this.payload        = payload;
        this.sequenceNumber = sequenceNumber;
        this.occurredAt     = occurredAt;
    }

    public UUID getId()             { return id; }
    public UUID getOrderId()        { return orderId; }
    public String getEventType()    { return eventType; }
    public String getPayload()      { return payload; }
    public long getSequenceNumber() { return sequenceNumber; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}
