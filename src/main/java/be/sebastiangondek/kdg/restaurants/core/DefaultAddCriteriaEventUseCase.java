package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.PriceRangeCriteriaEvent;
import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Appends a new price-range; history is kept in full.
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
