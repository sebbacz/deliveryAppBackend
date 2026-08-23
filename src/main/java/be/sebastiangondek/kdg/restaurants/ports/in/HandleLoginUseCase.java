package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.Owner;

// In-port for building an Owner domain record from jwt
public interface HandleLoginUseCase {

    Owner handleLogin(String subjectId, String email, String firstName, String lastName);
}
