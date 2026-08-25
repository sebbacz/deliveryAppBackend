package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.domain.PriceRangeCriteriaEvent;
import be.sebastiangondek.kdg.restaurants.ports.in.GetCriteriaEventsUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// Returns all criteria ordered by effectiveAt.
@Transactional(readOnly = true)
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
