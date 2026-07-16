package be.kdg.dishsg.pricerange.adapters.in.dto;

import java.time.LocalDateTime;
import java.util.UUID;

// Response DTO for a single price range criteria event including its ID, effective date, and thresholds.
public class CriteriaEventResponse {
    public UUID id;
    public LocalDateTime effectiveAt;
    public double cheapMax;
    public double regularMax;
    public double expensiveMax;

    public CriteriaEventResponse(UUID id, LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax) {
        this.id = id;
        this.effectiveAt = effectiveAt;
        this.cheapMax = cheapMax;
        this.regularMax = regularMax;
        this.expensiveMax = expensiveMax;
    }
}
