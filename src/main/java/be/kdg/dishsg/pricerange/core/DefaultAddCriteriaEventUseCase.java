package be.kdg.dishsg.pricerange.core;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventCmd;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventUseCase;
import be.kdg.dishsg.pricerange.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultAddCriteriaEventUseCase implements AddCriteriaEventUseCase {

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;

    public DefaultAddCriteriaEventUseCase(PriceRangeCriteriaEventRepositoryPort criteriaRepo) {
        this.criteriaRepo = criteriaRepo;
    }

    @Override
    public void addCriteriaEvent(AddCriteriaEventCmd cmd) {
        PriceRangeCriteriaEvent event = new PriceRangeCriteriaEvent(
                UUID.randomUUID(), cmd.effectiveAt(), cmd.cheapMax(), cmd.regularMax(), cmd.expensiveMax());
        criteriaRepo.save(event);
    }
}
