package be.kdg.dishsg.pricerange.ports.in;

import java.time.LocalDateTime;

// In-port for recording a new price range criteria change with its effective date and thresholds.
public interface AddCriteriaEventUseCase {
    void addCriteriaEvent(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax);
}
