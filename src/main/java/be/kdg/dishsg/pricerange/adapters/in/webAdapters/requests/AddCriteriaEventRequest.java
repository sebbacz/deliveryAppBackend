package be.kdg.dishsg.pricerange.adapters.in.webAdapters.requests;

import java.time.LocalDateTime;

// Request body for adding a new price range criteria event with effective date and price thresholds.
public class AddCriteriaEventRequest {
    public LocalDateTime effectiveAt;
    public double cheapMax;
    public double regularMax;
    public double expensiveMax;
}
