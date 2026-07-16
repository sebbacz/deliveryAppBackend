package be.kdg.dishsg.security.domain;

import java.util.UUID;

// Lightweight domain record identifying an authenticated restaurant owner.
public record Owner(UUID id, String email, String firstName, String lastName) {
}
