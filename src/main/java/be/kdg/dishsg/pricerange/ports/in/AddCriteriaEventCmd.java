package be.kdg.dishsg.pricerange.ports.in;

import java.time.LocalDateTime;

public record AddCriteriaEventCmd(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax) {}
