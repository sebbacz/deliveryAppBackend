package be.kdg.dishsg.order.adapters.out.eventStore;

import be.kdg.dishsg.order.adapters.out.eventStore.entities.OrderSnapshotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Spring Data JPA repository for loading and upserting order aggregate snapshots.
public interface SpringDataOrderSnapshotRepository extends JpaRepository<OrderSnapshotJpaEntity, UUID> {}
