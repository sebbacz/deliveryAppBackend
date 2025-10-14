package be.kdg.dishsg.restaurant.adapter.out;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class OwnerJpaEntity {

    @Id
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;

    protected OwnerJpaEntity() {}

    public OwnerJpaEntity(UUID id, String email, String firstName, String lastName) {
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
