package be.kdg.dishsg.security.application;


import be.kdg.dishsg.security.application.port.in.HandleLoginUseCase;
import be.kdg.dishsg.security.domain.Owner;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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
