package be.kdg.dishsg.restaurant.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerJpaRepository  extends JpaRepository<OwnerJpaEntity, UUID> {
    Optional<OwnerJpaEntity> findByEmail(String email);
}
