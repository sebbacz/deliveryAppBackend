package be.kdg.dishsg.security.domain;

import java.util.UUID;

// Domain record for a stored owner credential, including the BCrypt password hash.
public record OwnerAccount(UUID id, String email, String passwordHash, String firstName, String lastName) {
}
