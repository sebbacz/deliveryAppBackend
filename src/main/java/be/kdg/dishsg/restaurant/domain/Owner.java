package be.kdg.dishsg.restaurant.domain;

import java.util.UUID;

public class Owner {
    private final UUID id;
    private final String email;
    private final String firstName;
    private final String lastName;

    public Owner(UUID id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
