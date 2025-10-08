package be.kdg.dishsg.restaurant.port.out;

import be.kdg.dishsg.restaurant.domain.Owner;

public interface SaveOwnerPort {

    Owner save(Owner owner);
}
