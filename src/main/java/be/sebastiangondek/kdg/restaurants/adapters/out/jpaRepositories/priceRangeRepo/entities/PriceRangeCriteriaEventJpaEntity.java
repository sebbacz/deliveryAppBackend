package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.priceRangeRepo.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

// JPA entity storing a price range criteria event  CHEAP/REGULAR/EXPENSIVE/PREMIUM
@Entity
@Table(name = "price_range_criteria_events")
public class PriceRangeCriteriaEventJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime effectiveAt;

    @Column(nullable = false)
    private double cheapMax;

    @Column(nullable = false)
    private double regularMax;

    @Column(nullable = false)
    private double expensiveMax;

    protected PriceRangeCriteriaEventJpaEntity() {}

    public PriceRangeCriteriaEventJpaEntity(UUID id, LocalDateTime effectiveAt,
                                             double cheapMax, double regularMax, double expensiveMax) {
        this.id = id;
        this.effectiveAt = effectiveAt;
        this.cheapMax = cheapMax;
        this.regularMax = regularMax;
        this.expensiveMax = expensiveMax;
    }

    public UUID getId()                   { return id; }
    public LocalDateTime getEffectiveAt() { return effectiveAt; }
    public double getCheapMax()           { return cheapMax; }
    public double getRegularMax()         { return regularMax; }
    public double getExpensiveMax()       { return expensiveMax; }
}
