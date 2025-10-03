package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.exceptions.DishAlreadyPublishedException;
import be.kdg.dishsg.catalog.port.in.PublishDishCommand;
import be.kdg.dishsg.catalog.port.in.PublishDishUseCase;
import be.kdg.dishsg.catalog.port.out.DishEventPort;
import be.kdg.dishsg.catalog.port.out.DishPersistencePort;
import org.springframework.stereotype.Service;


@Service
public class PublishDishUseCaseImpl implements PublishDishUseCase {

    private final DishPersistencePort persistencePort;
    private final DishEventPort eventPort;

    public PublishDishUseCaseImpl(DishPersistencePort persistencePort, DishEventPort eventPort) {
        this.persistencePort = persistencePort;
        this.eventPort = eventPort;
    }


}