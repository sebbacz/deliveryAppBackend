package be.kdg.dishsg.catalog.core;


import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.exceptions.DishNotPublishedException;
import be.kdg.dishsg.catalog.port.in.UnpublishDishCommand;
import be.kdg.dishsg.catalog.port.out.DishEventPort;
import be.kdg.dishsg.catalog.port.out.DishPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class UnpublishDishUseCaseImpl {

    private final DishPersistencePort persistencePort;
    private final DishEventPort eventPort;

    public UnpublishDishUseCaseImpl(DishPersistencePort persistencePort, DishEventPort eventPort) {
        this.persistencePort = persistencePort;
        this.eventPort = eventPort;
    }


}