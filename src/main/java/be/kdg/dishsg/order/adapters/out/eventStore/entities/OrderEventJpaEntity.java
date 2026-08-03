package be.kdg.dishsg.order.adapters.out.eventStore.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

// JPA entity storing one serialized order domain event in the "order_events" table.
@Entity
@Table(
        name = "order_events",
        indexes = @Index(name = "idx_order_events_order_seq", columnList = "order_id, sequence_nr")
)
public class OrderEventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(name = "sequence_nr", nullable = false)
    private long sequenceNr;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    protected OrderEventJpaEntity() {}

    public OrderEventJpaEntity(UUID orderId, String eventType, String payload,
                               long sequenceNr, LocalDateTime occurredAt) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.payload = payload;
        this.sequenceNr = sequenceNr;
        this.occurredAt = occurredAt;
    }

    public Long getId()                  { return id; }
    public UUID getOrderId()             { return orderId; }
    public String getEventType()         { return eventType; }
    public String getPayload()           { return payload; }
    public long getSequenceNr()          { return sequenceNr; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}
