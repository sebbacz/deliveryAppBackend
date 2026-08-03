package be.kdg.dishsg.pricerange.ports.out;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;

import java.util.List;

// Out-port for persisting and retrieving price range criteria events.
public interface PriceRangeCriteriaEventRepositoryPort {
    void save(PriceRangeCriteriaEvent event);
    List<PriceRangeCriteriaEvent> findAllOrderedByEffectiveAt();
}
