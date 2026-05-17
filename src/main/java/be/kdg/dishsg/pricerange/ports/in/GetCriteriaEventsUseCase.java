package be.kdg.dishsg.pricerange.ports.in;

import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;

import java.util.List;

public interface GetCriteriaEventsUseCase {
    List<PriceRangeCriteriaEvent> getCriteriaEvents();
}
