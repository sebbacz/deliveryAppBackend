package be.kdg.dishsg.security.domain;

import java.util.UUID;

public record OwnerAccount(UUID id, String email, String passwordHash, String firstName, String lastName) {
}
