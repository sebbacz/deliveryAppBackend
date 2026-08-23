package be.sebastiangondek.kdg.orders.adapters.out.eventStore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// jpa repository for order event log; queries always return events in sequence order.
public interface SpringDataOrderEventRepository extends JpaRepository<OrderEventJpaEntity, UUID> {

    List<OrderEventJpaEntity> findByOrderIdOrderBySequenceNumberAsc(UUID orderId);

    List<OrderEventJpaEntity> findByOrderIdAndSequenceNumberGreaterThanOrderBySequenceNumberAsc(
            UUID orderId, long sequenceNumber);
}
