package be.kdg.dishsg.restaurant.core;


import be.kdg.dishsg.restaurant.domain.Owner;
import be.kdg.dishsg.restaurant.port.in.GetOrCreateOwnerProfileUseCase;
import be.kdg.dishsg.restaurant.port.out.LoadOwnerPort;
import be.kdg.dishsg.restaurant.port.out.SaveOwnerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OwnerAccessServiceImpl implements GetOrCreateOwnerProfileUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;

    public OwnerAccessServiceImpl(LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }

    @Override
    public OwnerProfile getOrCreate(Owner owner) {
        Owner existing = loadOwnerPort.loadById(owner.getId())
                .orElseGet(() -> saveOwnerPort.save(owner));

        boolean hasRestaurant = false;

        return new OwnerProfile(
                existing.getId().toString(),
                existing.getEmail(),
                existing.getFirstName(),
                existing.getLastName(),
                hasRestaurant
        );
    }
}
