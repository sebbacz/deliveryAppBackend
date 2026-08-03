package be.kdg.dishsg.order.adapters.out.eventStore.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

// JPA entity storing the latest Order aggregate snapshot in "order_snapshots" (one row per order).
@Entity
@Table(name = "order_snapshots")
public class OrderSnapshotJpaEntity {

    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "snapshot_data", nullable = false, columnDefinition = "TEXT")
    private String snapshotData;

    @Column(name = "version", nullable = false)
    private long version;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected OrderSnapshotJpaEntity() {}

    public OrderSnapshotJpaEntity(UUID orderId, String snapshotData, long version, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.snapshotData = snapshotData;
        this.version = version;
        this.createdAt = createdAt;
    }

    public UUID getOrderId()             { return orderId; }
    public String getSnapshotData()      { return snapshotData; }
    public long getVersion()             { return version; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    public void setSnapshotData(String snapshotData) { this.snapshotData = snapshotData; }
    public void setVersion(long version)              { this.version = version; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
