package be.kdg.dishsg.security.adapters.out.auth;

import be.kdg.dishsg.security.domain.Owner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// In-memory (ConcurrentHashMap) store mapping random UUID session tokens to Owner records.
@Component
public class OwnerSessionTokenStore {

    private final Map<String, Owner> sessions = new ConcurrentHashMap<>();

    public String issueToken(Owner owner) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, owner);
        return token;
    }

    public Optional<Owner> findOwnerByToken(String token) {
        return Optional.ofNullable(sessions.get(token));
    }
}
