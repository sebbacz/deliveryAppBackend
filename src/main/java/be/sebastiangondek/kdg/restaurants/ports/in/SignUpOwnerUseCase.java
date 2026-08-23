package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Owner;

// In-port for registering a new owner account with email, password, and name.
public interface SignUpOwnerUseCase {
    Owner signUp(String email, String rawPassword, String firstName, String lastName);
}
