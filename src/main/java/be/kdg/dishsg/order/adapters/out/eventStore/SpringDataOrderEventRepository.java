package be.kdg.dishsg.order.adapters.out.eventStore;

import be.kdg.dishsg.order.adapters.out.eventStore.entities.OrderEventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

// Spring Data JPA repository for order events; queries by order ID and sequence number for replay.
public interface SpringDataOrderEventRepository extends JpaRepository<OrderEventJpaEntity, Long> {

    List<OrderEventJpaEntity> findByOrderIdAndSequenceNrGreaterThanOrderBySequenceNrAsc(
            UUID orderId, long afterSequenceNr);
}
