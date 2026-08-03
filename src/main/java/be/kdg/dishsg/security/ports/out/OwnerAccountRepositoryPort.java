package be.kdg.dishsg.security.ports.out;

import be.kdg.dishsg.security.domain.OwnerAccount;

import java.util.Optional;
import java.util.UUID;

// Out-port for persisting owner credentials and looking them up by email or ID.
public interface OwnerAccountRepositoryPort {
    OwnerAccount save(OwnerAccount ownerAccount);
    Optional<OwnerAccount> findByEmail(String email);
    Optional<OwnerAccount> findById(UUID ownerId);
}
