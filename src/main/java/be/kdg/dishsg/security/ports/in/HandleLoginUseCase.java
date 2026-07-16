package be.kdg.dishsg.security.ports.in;

import be.kdg.dishsg.security.domain.Owner;

// In-port for building an Owner domain record from a Keycloak JWT subject and profile claims.
public interface HandleLoginUseCase {

    Owner handleLogin(String subjectId, String email, String firstName, String lastName);
}
