package be.kdg.dishsg.restaurant.port.out;

import be.kdg.dishsg.restaurant.domain.Owner;

import java.util.Optional;
import java.util.UUID;

public interface LoadOwnerPort {

    Optional<Owner> loadById(UUID id);
    Optional<Owner> loadByEmail(String email);
}
