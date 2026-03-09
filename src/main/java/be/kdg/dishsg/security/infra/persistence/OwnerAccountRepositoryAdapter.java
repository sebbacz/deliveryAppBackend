package be.kdg.dishsg.security.infra.persistence;

import be.kdg.dishsg.security.application.port.out.OwnerAccountRepositoryPort;
import be.kdg.dishsg.security.domain.OwnerAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class OwnerAccountRepositoryAdapter implements OwnerAccountRepositoryPort {

    private final SpringDataOwnerAccountRepository repository;

    public OwnerAccountRepositoryAdapter(SpringDataOwnerAccountRepository repository) {
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
