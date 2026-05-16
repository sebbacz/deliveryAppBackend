package be.kdg.dishsg.pricerange.ports.out;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;

import java.util.List;

public interface PriceRangeCriteriaEventRepositoryPort {
    void save(PriceRangeCriteriaEvent event);
    List<PriceRangeCriteriaEvent> findAllOrderedByEffectiveAt();
}
