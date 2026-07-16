package be.kdg.dishsg.pricerange.domain;

import java.time.LocalDateTime;
import java.util.UUID;

// Domain object recording a change in price range classification thresholds, effective from a given date.
public class PriceRangeCriteriaEvent {

    private final UUID id;
    private final LocalDateTime effectiveAt;
    private final double cheapMax;
    private final double regularMax;
    private final double expensiveMax;

    public PriceRangeCriteriaEvent(UUID id, LocalDateTime effectiveAt,
                                   double cheapMax, double regularMax, double expensiveMax) {
        this.id = id;
        this.effectiveAt = effectiveAt;
        this.cheapMax = cheapMax;
        this.regularMax = regularMax;
        this.expensiveMax = expensiveMax;
    }

    public PriceRange classify(double averagePrice) {
        if (averagePrice <= cheapMax) return PriceRange.CHEAP;
        if (averagePrice <= regularMax) return PriceRange.REGULAR;
        if (averagePrice <= expensiveMax) return PriceRange.EXPENSIVE;
        return PriceRange.PREMIUM;
    }

    public UUID getId()               { return id; }
    public LocalDateTime getEffectiveAt() { return effectiveAt; }
    public double getCheapMax()       { return cheapMax; }
    public double getRegularMax()     { return regularMax; }
    public double getExpensiveMax()   { return expensiveMax; }
}
