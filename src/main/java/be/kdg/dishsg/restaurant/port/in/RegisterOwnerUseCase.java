package be.kdg.dishsg.restaurant.port.in;

import be.kdg.dishsg.restaurant.domain.Owner;

import java.util.UUID;

public interface RegisterOwnerUseCase {
    Owner registerIfNotExists(UUID id, String email, String name);
}
