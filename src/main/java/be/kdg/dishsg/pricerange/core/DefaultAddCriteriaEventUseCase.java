package be.kdg.dishsg.pricerange.core;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventUseCase;
import be.kdg.dishsg.pricerange.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DefaultAddCriteriaEventUseCase implements AddCriteriaEventUseCase {

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;

    public DefaultAddCriteriaEventUseCase(PriceRangeCriteriaEventRepositoryPort criteriaRepo) {
        this.criteriaRepo = criteriaRepo;
    }

    @Override
    public void addCriteriaEvent(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax) {
        PriceRangeCriteriaEvent event = new PriceRangeCriteriaEvent(
                UUID.randomUUID(), effectiveAt, cheapMax, regularMax, expensiveMax);
        criteriaRepo.save(event);
    }
}
