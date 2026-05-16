package be.kdg.dishsg.pricerange.ports.in;

import java.time.LocalDateTime;

public interface AddCriteriaEventUseCase {
    void addCriteriaEvent(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax);
}
