package be.kdg.dishsg.security.application.port.in;

import be.kdg.dishsg.security.domain.Owner;

public interface SignUpOwnerUseCase {
    Owner signUp(String email, String rawPassword, String firstName, String lastName);
}
