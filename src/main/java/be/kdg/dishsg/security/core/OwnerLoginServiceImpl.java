package be.kdg.dishsg.security.core;

import be.kdg.dishsg.security.domain.Owner;
import be.kdg.dishsg.security.ports.in.HandleLoginUseCase;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

// Resolves a Keycloak JWT subject to an Owner record, converting non-UUID subjects via name-UUID.
@Service
public class OwnerLoginServiceImpl implements HandleLoginUseCase {

    @Override
    public Owner handleLogin(String subjectId, String email, String firstName, String lastName) {
        UUID ownerId;
        try {
            ownerId = UUID.fromString(subjectId);
        } catch (IllegalArgumentException ex) {
            ownerId = UUID.nameUUIDFromBytes(subjectId.getBytes(StandardCharsets.UTF_8));
        }
        return new Owner(ownerId, email, firstName, lastName);
    }
}
