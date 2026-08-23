package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.Owner;
import be.sebastiangondek.kdg.restaurants.ports.in.HandleLoginUseCase;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

// Resolves a Keycloak JWT subject to an Owner record
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
