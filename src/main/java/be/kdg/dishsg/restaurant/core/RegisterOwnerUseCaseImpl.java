package be.kdg.dishsg.restaurant.core;


import be.kdg.dishsg.restaurant.domain.Owner;
import be.kdg.dishsg.restaurant.port.in.RegisterOwnerUseCase;
import be.kdg.dishsg.restaurant.port.out.LoadOwnerPort;
import be.kdg.dishsg.restaurant.port.out.SaveOwnerPort;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
public class RegisterOwnerUseCaseImpl implements RegisterOwnerUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterOwnerUseCaseImpl.class);

    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;

    public RegisterOwnerUseCaseImpl(LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }

    @Override
    @Transactional
    public Owner registerIfNotExists(UUID id, String email, String name) {
        logger.info("Checking if owner with ID {} exists", id);

        return loadOwnerPort.loadById(id)
                .map(existingOwner -> {
                    logger.info("Owner {} already exists, skipping registration", email);
                    return existingOwner;
                })
                .orElseGet(() -> {
                    Owner newOwner = new Owner(id, email, name);
                    saveOwnerPort.save(newOwner);
                    logger.info("Registered new owner: {}", email);
                    return newOwner;
                });
    }
}
