package be.sebastiangondek.kdg.restaurants.ports.out;

import be.sebastiangondek.kdg.restaurants.domain.PriceRangeCriteriaEvent;

import java.util.List;

// Out-port for  retrieving price range criteria
public interface PriceRangeCriteriaEventRepositoryPort {
    void save(PriceRangeCriteriaEvent event);
    List<PriceRangeCriteriaEvent> findAllOrderedByEffectiveAt();
}
