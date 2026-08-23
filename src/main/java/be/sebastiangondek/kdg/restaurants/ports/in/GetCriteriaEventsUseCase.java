package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.PriceRangeCriteriaEvent;

import java.util.List;

// In-port for listing all historical price range criteria events in chronological order.
public interface GetCriteriaEventsUseCase {
    List<PriceRangeCriteriaEvent> getCriteriaEvents();
}
