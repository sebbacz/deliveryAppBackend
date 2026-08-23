package be.sebastiangondek.kdg.orders.adapters.out.eventStore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

//   state snapshot that lets the event store skip replaying events
@Entity
@Table(name = "order_snapshots")
public class OrderSnapshotJpaEntity {

    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    // Full JSON representation of OrderSnapshot at this point in time
    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(name = "sequence_number", nullable = false)
    private long sequenceNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected OrderSnapshotJpaEntity() {}

    public OrderSnapshotJpaEntity(UUID id, UUID orderId, String payload,
                                   long sequenceNumber, LocalDateTime createdAt) {
        this.id             = id;
        this.orderId        = orderId;
        this.payload        = payload;
        this.sequenceNumber = sequenceNumber;
        this.createdAt      = createdAt;
    }

    public UUID getId()             { return id; }
    public UUID getOrderId()        { return orderId; }
    public String getPayload()      { return payload; }
    public long getSequenceNumber() { return sequenceNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
