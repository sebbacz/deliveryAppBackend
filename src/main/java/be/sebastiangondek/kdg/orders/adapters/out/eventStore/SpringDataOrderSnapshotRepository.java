package be.sebastiangondek.kdg.orders.adapters.out.eventStore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//  JPA repository for order snapshots; findTopBy returns the latest snapshot to minimise event replay.
public interface SpringDataOrderSnapshotRepository extends JpaRepository<OrderSnapshotJpaEntity, UUID> {

    Optional<OrderSnapshotJpaEntity> findTopByOrderIdOrderBySequenceNumberDesc(UUID orderId);
}
