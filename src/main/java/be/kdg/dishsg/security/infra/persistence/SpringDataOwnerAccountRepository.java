package be.kdg.dishsg.security.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataOwnerAccountRepository extends JpaRepository<OwnerAccountJpaEntity, UUID> {
    Optional<OwnerAccountJpaEntity> findByEmail(String email);
}
