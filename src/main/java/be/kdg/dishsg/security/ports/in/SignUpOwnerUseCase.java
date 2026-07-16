package be.kdg.dishsg.security.ports.in;

import be.kdg.dishsg.security.domain.Owner;

// In-port for registering a new owner account with email, password, and name.
public interface SignUpOwnerUseCase {
    Owner signUp(String email, String rawPassword, String firstName, String lastName);
}
