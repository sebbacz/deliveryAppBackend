package be.kdg.dishsg.security.adapters.out.jpaRepositories.ownerRepo;

import be.kdg.dishsg.security.adapters.out.jpaRepositories.ownerRepo.entities.OwnerAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// Spring Data JPA repository for owner accounts with email-based lookup for sign-in.
public interface SpringDataOwnerAccountRepository extends JpaRepository<OwnerAccountJpaEntity, UUID> {
    Optional<OwnerAccountJpaEntity> findByEmail(String email);
}
