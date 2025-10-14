package be.kdg.dishsg.restaurant.port.in;

import be.kdg.dishsg.restaurant.domain.Owner;

public interface GetOrCreateOwnerProfileUseCase {

    OwnerProfile getOrCreate(Owner owner);

    record OwnerProfile(String id, String email, String firstName, String lastName, boolean hasRestaurant) {}
}

