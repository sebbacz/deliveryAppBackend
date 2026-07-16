package be.kdg.dishsg.pricerange.core;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;
import be.kdg.dishsg.pricerange.ports.in.GetCriteriaEventsUseCase;
import be.kdg.dishsg.pricerange.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGetCriteriaEventsUseCase implements GetCriteriaEventsUseCase {

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;

    public DefaultGetCriteriaEventsUseCase(PriceRangeCriteriaEventRepositoryPort criteriaRepo) {
        this.criteriaRepo = criteriaRepo;
    }

    @Override
    public List<PriceRangeCriteriaEvent> getCriteriaEvents() {
        return criteriaRepo.findAllOrderedByEffectiveAt();
    }
}
