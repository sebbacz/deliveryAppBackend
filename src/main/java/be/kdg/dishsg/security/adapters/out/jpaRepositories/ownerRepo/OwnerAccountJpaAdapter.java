package be.kdg.dishsg.security.adapters.out.jpaRepositories.ownerRepo;

import be.kdg.dishsg.security.adapters.out.jpaRepositories.ownerRepo.entities.OwnerAccountJpaEntity;
import be.kdg.dishsg.security.domain.OwnerAccount;
import be.kdg.dishsg.security.ports.out.OwnerAccountRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// JPA adapter mapping OwnerAccount domain records to/from OwnerAccountJpaEntity.
@Repository
public class OwnerAccountJpaAdapter implements OwnerAccountRepositoryPort {

    private final SpringDataOwnerAccountRepository repository;

    public OwnerAccountJpaAdapter(SpringDataOwnerAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public OwnerAccount save(OwnerAccount ownerAccount) {
        OwnerAccountJpaEntity saved = repository.save(toEntity(ownerAccount));
        return toDomain(saved);
    }

    @Override
    public Optional<OwnerAccount> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<OwnerAccount> findById(UUID ownerId) {
        return repository.findById(ownerId).map(this::toDomain);
    }

    private OwnerAccountJpaEntity toEntity(OwnerAccount ownerAccount) {
        return new OwnerAccountJpaEntity(
                ownerAccount.id(),
                ownerAccount.email(),
                ownerAccount.passwordHash(),
                ownerAccount.firstName(),
                ownerAccount.lastName()
        );
    }

    private OwnerAccount toDomain(OwnerAccountJpaEntity entity) {
        return new OwnerAccount(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }
}
