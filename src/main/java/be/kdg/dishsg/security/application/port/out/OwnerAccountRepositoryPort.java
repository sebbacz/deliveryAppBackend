package be.kdg.dishsg.security.application.port.out;

import be.kdg.dishsg.security.domain.OwnerAccount;

import java.util.Optional;
import java.util.UUID;

public interface OwnerAccountRepositoryPort {
    OwnerAccount save(OwnerAccount ownerAccount);
    Optional<OwnerAccount> findByEmail(String email);
    Optional<OwnerAccount> findById(UUID ownerId);
}
