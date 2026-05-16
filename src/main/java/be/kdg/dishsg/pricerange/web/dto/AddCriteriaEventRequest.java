package be.kdg.dishsg.pricerange.web.dto;

import java.time.LocalDateTime;

public class AddCriteriaEventRequest {
    public LocalDateTime effectiveAt;
    public double cheapMax;
    public double regularMax;
    public double expensiveMax;
}
