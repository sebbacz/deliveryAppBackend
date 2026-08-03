package be.kdg.dishsg.security.adapters.out.jpaRepositories.ownerRepo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

// JPA entity for owner credentials stored in the "owner_accounts" table.
@Entity
@Table(name = "owner_accounts")
public class OwnerAccountJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    protected OwnerAccountJpaEntity() {
    }

    public OwnerAccountJpaEntity(UUID id, String email, String passwordHash, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId()            { return id; }
    public String getEmail()       { return email; }
    public String getPasswordHash(){ return passwordHash; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
}
