package be.kdg.dishsg.catalog.core;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.port.in.SaveDishDraftCommand;
import be.kdg.dishsg.catalog.port.in.SaveDishDraftUseCase;
import be.kdg.dishsg.catalog.port.out.DishPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class SaveDishDraftUseCaseImpl implements SaveDishDraftUseCase {

    private final DishPersistencePort persistencePort;

    public SaveDishDraftUseCaseImpl(DishPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }


}