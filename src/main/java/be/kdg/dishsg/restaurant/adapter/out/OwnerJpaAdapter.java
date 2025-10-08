package be.kdg.dishsg.restaurant.adapter.out;


import be.kdg.dishsg.restaurant.domain.Owner;
import be.kdg.dishsg.restaurant.port.out.LoadOwnerPort;
import be.kdg.dishsg.restaurant.port.out.SaveOwnerPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OwnerJpaAdapter   implements LoadOwnerPort, SaveOwnerPort {

    private final OwnerJpaRepository repository;

    public OwnerJpaAdapter(OwnerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Owner> loadById(UUID id) {
        return repository.findById(id)
                .map(e -> new Owner(e.getId(), e.getEmail(), e.getName()));
    }

    @Override
    public Optional<Owner> loadByEmail(String email) {
        return repository.findByEmail(email)
                .map(e -> new Owner(e.getId(), e.getEmail(), e.getName()));
    }

    @Override
    public Owner save(Owner owner) {
        OwnerJpaEntity entity = new OwnerJpaEntity(owner.getId(), owner.getEmail(), owner.getName());
        OwnerJpaEntity saved = repository.save(entity);
        return new Owner(saved.getId(), saved.getEmail(), saved.getName());
    }
}
