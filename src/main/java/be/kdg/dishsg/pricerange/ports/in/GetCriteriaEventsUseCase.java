package be.kdg.dishsg.pricerange.ports.in;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;

import java.util.List;

// In-port for listing all historical price range criteria events in chronological order.
public interface GetCriteriaEventsUseCase {
    List<PriceRangeCriteriaEvent> getCriteriaEvents();
}
