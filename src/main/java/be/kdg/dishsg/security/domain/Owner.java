package be.kdg.dishsg.security.domain;

import java.util.UUID;

public record Owner(UUID id, String email, String firstName, String lastName) {
}
