package be.kdg.dishsg.restaurant.domain;

import java.util.UUID;

public class Owner {
    private final UUID id;
    private final String email;
    private final String name;

    public Owner(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
